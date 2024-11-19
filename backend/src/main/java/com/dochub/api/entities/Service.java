package com.dochub.api.entities;

import com.dochub.api.dtos.service.CreateServiceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "SERVICO")
public class Service {
    @Id
    @Column(name = "ID_SERVICO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DESCRICAO", nullable = false, length = 256)
    private String description;

    @Embedded
    private AuditRecord auditRecord;

    public Service (final CreateServiceDTO createServiceDTO, final String initiatorUsername) {
        this.description = createServiceDTO.description();

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}