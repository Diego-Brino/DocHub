package com.dochub.api.entities;

import com.dochub.api.dtos.archive.CreateArchiveDTO;
import com.dochub.api.dtos.resource_movement.CreateResourceMovementDTO;
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
@Table(schema = "DOCHUB", name = "ARQUIVO")
public class Archive {
    @Id
    @Column(name = "ID_ARQUIVO")
    private Integer id;

    @MapsId
    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "ID_ARQUIVO", nullable = false)
    private Resource resource;

    @Column(name = "ID_S3_OBJECT", length = 256)
    private String s3Hash;

    @Column(name = "TIPO", length = 1000, nullable = false)
    private String type;

    @Column(name = "TAMANHO", nullable = false)
    private Long length;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PASTA")
    private Folder folder;

    @Embedded
    private AuditRecord auditRecord;

    public Archive (final CreateArchiveDTO createArchiveDTO, final Folder folder, final String initiatorUsername) {
        this.s3Hash = createArchiveDTO.hashS3();
        this.type = createArchiveDTO.contentType();
        this.length = createArchiveDTO.length();

        if (Objects.nonNull(folder)) {
            this.folder = folder;
        }

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }

    public Archive (final CreateResourceMovementDTO createResourceMovementDTO, final String initiatorUsername) {
        this.s3Hash = createResourceMovementDTO.hashS3();
        this.type = createResourceMovementDTO.contentType();
        this.length = createResourceMovementDTO.length();

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }

    public String getPath () {
        if (this.folder != null) {
            return this.folder.getPath() + "/" + this.resource.getName();
        } else {
            return "Raiz/" + this.resource.getName();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Archive archive = (Archive) o;
        return Objects.equals(id, archive.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}