package com.dochub.api.entities;

import com.dochub.api.entities.flow_user_role.FlowUserRole;
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
    private List<FlowUserRole> flowUserRoles;

    @Embedded
    private AuditRecord auditRecord;
}