package com.dochub.api.entities;

import com.dochub.api.dtos.group.CreateGroupDTO;
import com.dochub.api.utils.Utils;
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
@Table(schema = "DOCHUB", name = "GRUPO")
public class Group {
    @Id
    @Column(name = "ID_GRUPO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "AVATAR")
    private byte[] avatar;

    @Embedded
    private AuditRecord auditRecord;

    public Group (final CreateGroupDTO createGroupDTO, final String initiatorUsername) {
        this.name = createGroupDTO.name();
        this.description = createGroupDTO.description();

        if (Objects.nonNull(createGroupDTO.avatar())) {
            this.avatar = Utils.readBytesFromMultipartFile(createGroupDTO.avatar());
        }

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}