package com.dochub.api.entities;

import com.dochub.api.dtos.flow.CreateFlowDTO;
import com.dochub.api.entities.flow_user.FlowUser;
import com.dochub.api.entities.response_flow.ResponseFlow;
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
@Table(schema = "DOCHUB", name = "FLUXO")
public class Flow {
    @Id
    @Column(name = "ID_FLUXO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ORDEM")
    private Integer order;

    @Column(name = "PRAZO")
    private Integer time;

    @Column(name = "DATA_LIMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date limitDate;

    @ManyToOne
    @JoinColumn(name = "ID_PROCESSO", nullable = false)
    private Process process;

    @ManyToOne
    @JoinColumn(name = "ID_ATIVIDADE", nullable = false)
    private Activity activity;

    @OneToMany(mappedBy = "flow", cascade = { CascadeType.REMOVE })
    private List<ResponseFlow> responseFlows;

    @OneToMany(mappedBy = "flow", cascade = { CascadeType.REMOVE })
    private List<FlowUser> flowUsers;

    @Embedded
    private AuditRecord auditRecord;

    public Flow (final CreateFlowDTO createFlowDTO, final Process process, final Activity activity, final String initiatorUsername) {
        this.order = createFlowDTO.order();
        this.time = createFlowDTO.time();
        this.limitDate = createFlowDTO.limitDate();
        this.process = process;
        this.activity = activity;

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}