package com.dochub.api.services;

import com.dochub.api.dtos.group.CreateGroupDTO;
import com.dochub.api.dtos.group.GroupResponseDTO;
import com.dochub.api.dtos.group.UpdateGroupDTO;
import com.dochub.api.dtos.group_permission.GroupPermissionResponseDTO;
import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.User;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermission;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.GroupRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.S3Utils;
import com.dochub.api.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Group getById (final Integer groupId) {
        return groupRepository
            .findById(groupId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public GroupResponseDTO getDtoById (final Integer groupId) {
        final Group group = getById(groupId);

        return new GroupResponseDTO(group);
    }

    public List<GroupResponseDTO> getGroupsByUserWithViewPermission (final User user) {
        final List<Group> groups = groupRepository.findGroupsByUserWithViewPermission(user);

        return groups
            .stream()
            .map(GroupResponseDTO::new)
            .collect(Collectors.toList());
    }

    public byte[] getGroupAvatar (final Integer groupId) {
        final Group group = getById(groupId);

        if (Objects.nonNull(group.getAvatar())) {
            return group.getAvatar();
        }

        return Utils.getImageFromClasspath(Constants.GROUP_ICON_PATH);
    }

    @Transactional
    public Integer create (final UserRoleResponseDTO userRoles, final CreateGroupDTO createGroupDTO,
                           final Consumer<String> createS3BucketFunc,
                           final Function<String, RoleResponseDTO> getRoleByNameFunc,
                           final Function<String, GroupPermissionResponseDTO> getGroupPermissionByDescriptionFunc,
                           final TriConsumer<Function<String, RoleResponseDTO>, Function<String, GroupPermissionResponseDTO>, Integer> assignViewPermissionToAdminFunc) {
        Utils.checkPermission(userRoles, Constants.CREATE_GROUP_PERMISSION);

        Utils.validateImageType(createGroupDTO.avatar());

        final String idS3Bucket = S3Utils.generateBucketName();

        final Group group = new Group(createGroupDTO, idS3Bucket, userRoles.user().username());

        final Integer groupId = groupRepository.save(group).getId();

        assignViewPermissionToAdminFunc.accept(getRoleByNameFunc, getGroupPermissionByDescriptionFunc, groupId);

        createS3BucketFunc.accept(idS3Bucket);

        return groupId;
    }

    public void update (final UserRoleResponseDTO userRoles, final Integer groupId, final UpdateGroupDTO updateGroupDTO) {
        Utils.checkPermission(userRoles, groupId, Constants.EDIT_GROUP_PERMISSION);

        Utils.validateImageType(updateGroupDTO.avatar());

        final Group group = getById(groupId);

        Utils.updateFieldIfPresent(updateGroupDTO.name(), group::setName);
        Utils.updateFieldIfPresent(updateGroupDTO.description(), group::setDescription);
        _updateAvatarIfPresent(updateGroupDTO.avatar(), group);

        _logAuditForChange(group, userRoles.user().username());

        groupRepository.save(group);
    }

    public void updateAvatar (final UserRoleResponseDTO userRoles, final Integer groupId, final MultipartFile avatar) {
        Utils.checkPermission(userRoles, groupId, Constants.EDIT_GROUP_PERMISSION);

        Utils.validateImageType(avatar);

        final Group group = getById(groupId);

        group.setAvatar(Utils.readBytesFromMultipartFile(avatar));

        _logAuditForChange(group, userRoles.user().username());

        groupRepository.save(group);
    }

    @Transactional
    public void delete (final UserRoleResponseDTO userRoles, final Integer groupId,
                        final Consumer<String> deleteBucketWithContentsAsyncFunc,
                        final Consumer<Group> deleteAllGroupRolePermissionsAssignedToGroupFunc,
                        final Function<Resource, List<ResourceRolePermission>> getAllByResourceFunc,
                        final Consumer<List<ResourceRolePermission>> deleteResourceRolePermissionsFunc,
                        final Consumer<Group> deleteAllResourceHistoriesAssignedToGroupFunc,
                        final Consumer<Group> deleteAllResourceMovementsAssignedToGroupFunc,
                        final Consumer<Group> deleteAllMovementsAssignedToGroupFunc,
                        final Consumer<Group> deleteAllRequestsAssignedToGroupFunc,
                        final Consumer<Group> deleteAllResponseFlowsAssignedToGroupFunc,
                        final Consumer<Group> deleteAllProcessAssignedToGroupFunc,
                        final TriConsumer<Group, Function<Resource, List<ResourceRolePermission>>, Consumer<List<ResourceRolePermission>>> deleteAllArchivesAssignedToGroupFunc,
                        final TriConsumer<Group, Function<Resource, List<ResourceRolePermission>>, Consumer<List<ResourceRolePermission>>> deleteAllFoldersAssignedToGroupFunc) {
        final Group group = getById(groupId);

        Utils.checkPermission(userRoles, groupId, Constants.DELETE_GROUP_PERMISSION);

        deleteBucketWithContentsAsyncFunc.accept(group.getIdS3Bucket());
        deleteAllGroupRolePermissionsAssignedToGroupFunc.accept(group);
        deleteAllResourceHistoriesAssignedToGroupFunc.accept(group);
        deleteAllResourceMovementsAssignedToGroupFunc.accept(group);
        deleteAllMovementsAssignedToGroupFunc.accept(group);
        deleteAllRequestsAssignedToGroupFunc.accept(group);
        deleteAllResponseFlowsAssignedToGroupFunc.accept(group);
        deleteAllProcessAssignedToGroupFunc.accept(group);
        deleteAllArchivesAssignedToGroupFunc.accept(group, getAllByResourceFunc, deleteResourceRolePermissionsFunc);
        deleteAllFoldersAssignedToGroupFunc.accept(group, getAllByResourceFunc, deleteResourceRolePermissionsFunc);

        groupRepository.delete(group);
    }

    private void _updateAvatarIfPresent (final MultipartFile avatar, final Group group) {
        if (Objects.nonNull(avatar)) {
            group.setAvatar(Utils.readBytesFromMultipartFile(avatar));
        }
    }

    private void _logAuditForChange (final Group group, final String actor) {
        group.getAuditRecord().setAlterationUser(actor);
        group.getAuditRecord().setAlterationDate(new Date());
    }
}