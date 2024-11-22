package com.dochub.api.entities;

import com.dochub.api.dtos.response.CreateResponseDTO;
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
@Table(schema = "DOCHUB", name = "RESPOSTA")
public class Response {
    @Id
    @Column(name = "ID_RESPOSTA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DESCRICAO")
    private String description;

    @Embedded
    private AuditRecord auditRecord;

    public Response (final CreateResponseDTO createResponseDTO, final String initiatorUsername) {
        this.description = createResponseDTO.description();

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}