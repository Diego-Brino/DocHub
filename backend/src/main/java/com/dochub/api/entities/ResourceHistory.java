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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RECURSO", nullable = false)
    private Resource resource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PASTA_ANTERIOR")
    private Folder previousFolder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PASTA_ATUAL")
    private Folder currentFolder;

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
}