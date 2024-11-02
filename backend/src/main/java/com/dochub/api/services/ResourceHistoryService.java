package com.dochub.api.services;

import com.dochub.api.entities.Folder;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.ResourceHistory;
import com.dochub.api.enums.ResourceHistoryActionType;
import com.dochub.api.repositories.ResourceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ResourceHistoryService {
    private final ResourceHistoryRepository resourceHistoryRepository;

    public void create (final Resource resource,
                        final Folder currentFolder,
                        final ResourceHistoryActionType actionType, final String actionDescription, final String actionUsername) {
        create(resource, null, currentFolder, actionType, actionDescription, actionUsername);
    }

    public void create (final Resource resource,
                        final Folder previousFolder, final Folder currentFolder,
                        final ResourceHistoryActionType actionType, final String actionUsername) {
        create(resource, previousFolder, currentFolder, actionType, null, actionUsername);
    }

    private void create (final Resource resource,
                        final Folder previousFolder, final Folder currentFolder,
                        final ResourceHistoryActionType actionType, final String actionDescription, final String actionUsername) {
        final ResourceHistory resourceHistory = new ResourceHistory();

        resourceHistory.setResource(resource);
        resourceHistory.setPreviousFolder(previousFolder);
        resourceHistory.setCurrentFolder(currentFolder);
        resourceHistory.setActionType(actionType);
        resourceHistory.setDescription(actionDescription);
        resourceHistory.setActionUser(actionUsername);
        resourceHistory.setActionDate(new Date());

        resourceHistoryRepository.save(resourceHistory);
    }
}