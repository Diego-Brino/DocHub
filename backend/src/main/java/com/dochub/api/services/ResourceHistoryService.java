package com.dochub.api.services;

import com.dochub.api.dtos.resource_history.ResourceHistoryResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.ResourceHistory;
import com.dochub.api.enums.ResourceHistoryActionType;
import com.dochub.api.repositories.ResourceHistoryRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceHistoryService {
    private final ResourceHistoryRepository resourceHistoryRepository;

    public List<ResourceHistoryResponseDTO> getAllByGroup (final UserRoleResponseDTO userRoles, final Group group) {
        Utils.checkPermission(userRoles, group.getId(), Constants.VIEW_RESOURCES_HISTORY_PERMISSION);

        final List<ResourceHistory> resourceHistories = resourceHistoryRepository
            .findAllByGroupOrderByActionDateDesc(group)
            .orElse(Collections.emptyList());

        return resourceHistories
            .stream()
            .map(ResourceHistoryResponseDTO::new)
            .collect(Collectors.toList());
    }

    public void logCreationResourceHistory (final Group group, final String description, final String actionUser) {
        final ResourceHistory resourceHistory = new ResourceHistory(group, description, actionUser);

        resourceHistory.setActionType(ResourceHistoryActionType.CREATED);

        resourceHistoryRepository.save(resourceHistory);
    }

    public void logEditResourceHistory (final Group group, final String description, final String actionUser) {
        final ResourceHistory resourceHistory = new ResourceHistory(group, description, actionUser);

        resourceHistory.setActionType(ResourceHistoryActionType.EDITED);

        resourceHistoryRepository.save(resourceHistory);
    }

    public void logDeletionResourceHistory (final Group group, final String description, final String actionUser) {
        final ResourceHistory resourceHistory = new ResourceHistory(group, description, actionUser);

        resourceHistory.setActionType(ResourceHistoryActionType.DELETED);

        resourceHistoryRepository.save(resourceHistory);
    }

    public void deleteAllAssignedToGroup (final Group group) {
        final List<ResourceHistory> resourceHistories = resourceHistoryRepository
            .findAllByGroupOrderByActionDateDesc(group)
            .orElse(Collections.emptyList());

        if (!resourceHistories.isEmpty()) {
            resourceHistoryRepository.deleteAll(resourceHistories);
        }
    }
}