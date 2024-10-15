package com.dochub.api.repositories;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    Integer countResourceByGroup (Group group);
    Optional<List<Resource>> findByGroupAndArchive_FolderIsNullAndFolder_ParentFolderIsNull (Group group);
}