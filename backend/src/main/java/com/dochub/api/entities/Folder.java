package com.dochub.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "PASTA")
public class Folder {
    @Id
    @Column(name = "ID_PASTA")
    private Integer id;

    @MapsId
    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "ID_PASTA", nullable = false)
    private Resource resource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PASTA_PAI")
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Folder> subFolders;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Archive> archives;

    @Embedded
    private AuditRecord auditRecord;

    public Folder (final Folder parentFolder, final String initiatorUsername) {
        if (Objects.nonNull(parentFolder)) {
            this.parentFolder = parentFolder;
        }

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return Objects.equals(id, folder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}