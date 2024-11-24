package com.dochub.api.entities;

import com.dochub.api.converters.RequestStatusConverter;
import com.dochub.api.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "SOLICITACAO")
public class Request {
    @Id
    @Column(name = "ID_SOLICITACAO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ID_PROCESSO", nullable = false)
    private Process process;

    @Column(name = "STATUS")
    @Convert(converter = RequestStatusConverter.class)
    private RequestStatus status;

    @OneToMany(mappedBy = "request", cascade = { CascadeType.REMOVE })
    private List<Movement> movements;

    @Embedded
    private AuditRecord auditRecord;

    public Request (final User user, final Process process, final String initiatorUsername) {
        this.user = user;
        this.process = process;
        this.status = RequestStatus.IN_PROGRESS;

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}