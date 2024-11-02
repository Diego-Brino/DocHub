package com.dochub.api.entities;

import com.dochub.api.converters.ResourceHistoryActionTypeConverter;
import com.dochub.api.enums.ResourceHistoryActionType;
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
@Table(schema = "DOCHUB", name = "HISTORICO")
public class ResourceHistory {
    @Id
    @Column(name = "ID_HISTORICO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Group group;

    @Column(name = "TIPO_MOVIMENTACAO", nullable = false)
    @Convert(converter = ResourceHistoryActionTypeConverter.class)
    private ResourceHistoryActionType actionType;

    @Column(name = "DESCRICAO", length = 256)
    private String description;

    @Column(name = "USUARIO_MOVIMENTACAO", length = 100, nullable = false)
    private String actionUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_MOVIMENTACAO", nullable = false)
    private Date actionDate;

    public ResourceHistory (final Group group, final String description, final String actionUser) {
        this.group = group;
        this.description = description;
        this.actionUser = actionUser;
        this.actionDate = new Date();
    }
}