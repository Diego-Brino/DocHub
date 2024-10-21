package com.dochub.api.services;

import com.dochub.api.dtos.group.CreateGroupDTO;
import com.dochub.api.dtos.group.GroupResponseDTO;
import com.dochub.api.dtos.group.UpdateGroupDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.User;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.GroupCannontBeDeletedException;
import com.dochub.api.repositories.GroupRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    public Integer create (final UserRoleResponseDTO userRoles, final CreateGroupDTO createGroupDTO) {
        Utils.checkPermission(userRoles, Constants.CREATE_GROUP_PERMISSION);

        Utils.validateImageType(createGroupDTO.avatar());

        final Group group = new Group(createGroupDTO, userRoles.user().username());

        return groupRepository.save(group).getId();
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

    public void delete (final Function<Group, Boolean> hasGroupRolePermissionsAssignedToGroupFunc,
                        final Function<Group, Boolean> hasResourcesAssignedToGroupFunc,
                        final Function<Group, Boolean> hasProcessesAssignedToGroupFunc,
                        final UserRoleResponseDTO userRoles, final Integer groupId) {
        final Group group = getById(groupId);

        final Boolean hasGroupRolePermissionsAssignedToGroup = hasGroupRolePermissionsAssignedToGroupFunc.apply(group);
        final Boolean hasResourcesAssignedToGroup = hasResourcesAssignedToGroupFunc.apply(group);
        final Boolean hasProcessesAssignedToGroup = hasProcessesAssignedToGroupFunc.apply(group);

        if (hasGroupRolePermissionsAssignedToGroup || hasResourcesAssignedToGroup || hasProcessesAssignedToGroup) {
            throw new GroupCannontBeDeletedException();
        }

        Utils.checkPermission(userRoles, groupId, Constants.DELETE_GROUP_PERMISSION);

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