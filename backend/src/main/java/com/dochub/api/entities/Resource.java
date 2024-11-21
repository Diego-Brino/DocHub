package com.dochub.api.entities;

import com.dochub.api.converters.ResourceOriginConverter;
import com.dochub.api.dtos.archive.CreateArchiveDTO;
import com.dochub.api.dtos.folder.CreateFolderDTO;
import com.dochub.api.enums.ResourceOrigin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "RECURSO")
public class Resource {
    @Id
    @Column(name = "ID_RECURSO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME", length = 128, nullable = false)
    private String name;

    @Column(name = "DESCRICAO", length = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Group group;

    @Column(name = "ORIGEM")
    @Convert(converter = ResourceOriginConverter.class)
    private ResourceOrigin origin;

    @OneToOne(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Archive archive;

    @OneToOne(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Folder folder;

    @Embedded
    private AuditRecord auditRecord;

    public Resource (final CreateArchiveDTO createArchiveDTO, final Group group, final String initiatorUsername) {
        this.name = createArchiveDTO.name();
        this.description = createArchiveDTO.description();
        this.group = group;

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }

    public Resource (final CreateFolderDTO createFolderDTO, final Group group, final String initiatorUsername) {
        this.name = createFolderDTO.name();
        this.description = createFolderDTO.description();
        this.group = group;

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }

    @PrePersist
    public void prePersist () {
        if (Objects.isNull(this.origin)) {
            this.origin = ResourceOrigin.GROUP;
        }
    }
}