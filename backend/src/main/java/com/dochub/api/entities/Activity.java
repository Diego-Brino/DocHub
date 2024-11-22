package com.dochub.api.entities;

import com.dochub.api.dtos.activity.CreateActivityDTO;
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
@Table(schema = "DOCHUB", name = "ATIVIDADE")
public class Activity {
    @Id
    @Column(name = "ID_ATIVIDADE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DESCRICAO")
    private String description;

    @Embedded
    private AuditRecord auditRecord;

    public Activity (final CreateActivityDTO createActivityDTO, final String initiatorUsername) {
        this.description = createActivityDTO.description();

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}