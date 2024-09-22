package com.dochub.api.services;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.Resource;
import com.dochub.api.repositories.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public Boolean hasResourcesAssignedToGroup (final Group group) {
        final List<Resource> resources = resourceRepository
            .findByGroup(group)
            .orElse(Collections.emptyList());

        if (resources.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }
}