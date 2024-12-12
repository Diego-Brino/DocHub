package com.dochub.api.repositories;

import com.dochub.api.entities.Activity;
import com.dochub.api.entities.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Integer> {
    Optional<List<Flow>> findByActivity (Activity activity);

    @Query(value = "WITH " +
            "W_DADOS AS ( " +
            "  SELECT S.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, FR.ID_RESPOSTA, FR.ID_FLUXO_DESTINO " +
            "  FROM SOLICITACAO S " +
            "  JOIN PROCESSO P ON (P.ID_PROCESSO = S.ID_PROCESSO) " +
            "  JOIN FLUXO F ON (F.ID_PROCESSO = P.ID_PROCESSO) " +
            "  JOIN FLUXO_RESPOSTA FR ON (FR.ID_FLUXO = F.ID_FLUXO) " +
            "  JOIN USUARIO_FLUXO UR ON (UR.ID_FLUXO = F.ID_FLUXO) " +
            "  WHERE S.STATUS = 'EM ANDAMENTO' " +
            "  AND UR.ID_USUARIO = :userId " +
            "  GROUP BY S.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, FR.ID_RESPOSTA, FR.ID_FLUXO_DESTINO " +
            "), " +
            "W_PRIMEIRO AS ( " +
            "  SELECT D.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, F.ID_PROCESSO, F.ID_ATIVIDADE, F.USUARIO_INSERCAO, F.DATA_INSERCAO, F.USUARIO_ALTERACAO, F.DATA_ALTERACAO " +
            "  FROM W_DADOS D " +
            "  LEFT JOIN MOVIMENTO M ON (M.ID_SOLICITACAO = D.ID_SOLICITACAO) " +
            "  JOIN FLUXO F ON (F.ID_FLUXO = D.ID_FLUXO) " +
            "  WHERE (M.ID_MOVIMENTO IS NULL AND D.ORDEM = 1) " +
            "), " +
            "W_EM_ANDAMEN AS ( " +
            "  SELECT D.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, F.ID_PROCESSO, F.ID_ATIVIDADE, F.USUARIO_INSERCAO, F.DATA_INSERCAO, F.USUARIO_ALTERACAO, F.DATA_ALTERACAO " +
            "  FROM W_DADOS D " +
            "  JOIN MOVIMENTO M ON (M.ID_SOLICITACAO = D.ID_SOLICITACAO AND M.ID_FLUXO = D.ID_FLUXO AND M.ID_RESPOSTA = D.ID_RESPOSTA) " +
            "  JOIN FLUXO F ON (F.ID_FLUXO = D.ID_FLUXO_DESTINO) " +
            "  GROUP BY D.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, F.ID_PROCESSO, F.ID_ATIVIDADE, F.USUARIO_INSERCAO, F.DATA_INSERCAO, F.USUARIO_ALTERACAO, F.DATA_ALTERACAO " +
            "  ORDER BY F.ORDEM DESC " +
            "  LIMIT 1 " +
            "), " +
            "W_RESULT AS ( " +
            "  SELECT F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, F.ID_PROCESSO, F.ID_ATIVIDADE, F.USUARIO_INSERCAO, F.DATA_INSERCAO, F.USUARIO_ALTERACAO, F.DATA_ALTERACAO " +
            "  FROM ( " +
            "    SELECT * FROM W_PRIMEIRO " +
            "    UNION " +
            "    SELECT * FROM W_EM_ANDAMEN " +
            "  ) F " +
            "  ORDER BY F.ID_SOLICITACAO " +
            ")" +
            "SELECT * FROM W_RESULT", nativeQuery = true)
    Optional<List<Flow>> findAssignedFlowsByUser (@Param("userId") Integer userId);


    @Query(value = """
            WITH 
            W_DADOS AS (
                SELECT S.ID_SOLICITACAO, 
                       F.ID_FLUXO, 
                       F.ORDEM, 
                       F.PRAZO, 
                       F.DATA_LIMITE, 
                       FR.ID_RESPOSTA, 
                       FR.ID_FLUXO_DESTINO
                FROM solicitacao S
                JOIN processo P ON P.ID_PROCESSO = S.ID_PROCESSO
                JOIN fluxo F ON F.ID_PROCESSO = P.ID_PROCESSO
                JOIN fluxo_resposta FR ON FR.ID_FLUXO = F.ID_FLUXO
                WHERE S.STATUS = 'EM ANDAMENTO'
                  AND S.ID_SOLICITACAO = :requestId
                GROUP BY S.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, FR.ID_RESPOSTA, FR.ID_FLUXO_DESTINO
                ORDER BY S.ID_SOLICITACAO ASC, F.ID_FLUXO ASC
            ),
            W_PRIMEIRO AS (
                SELECT D.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, 
                       F.ID_PROCESSO, F.ID_ATIVIDADE, F.USUARIO_INSERCAO, F.DATA_INSERCAO, 
                       F.USUARIO_ALTERACAO, F.DATA_ALTERACAO
                FROM W_DADOS D
                LEFT JOIN movimento M ON M.ID_SOLICITACAO = D.ID_SOLICITACAO
                JOIN fluxo F ON F.ID_FLUXO = D.ID_FLUXO
                WHERE M.ID_MOVIMENTO IS NULL AND D.ORDEM = 1
                GROUP BY D.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, 
                         F.ID_PROCESSO, F.ID_ATIVIDADE, F.USUARIO_INSERCAO, F.DATA_INSERCAO, 
                         F.USUARIO_ALTERACAO, F.DATA_ALTERACAO
            ),
            W_EM_ANDAMEN AS (
                SELECT D.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, 
                       F.ID_PROCESSO, F.ID_ATIVIDADE, F.USUARIO_INSERCAO, F.DATA_INSERCAO, 
                       F.USUARIO_ALTERACAO, F.DATA_ALTERACAO
                FROM W_DADOS D
                JOIN movimento M ON M.ID_SOLICITACAO = D.ID_SOLICITACAO AND 
                                     M.ID_FLUXO = D.ID_FLUXO AND 
                                     M.ID_RESPOSTA = D.ID_RESPOSTA
                JOIN fluxo F ON F.ID_FLUXO = D.ID_FLUXO_DESTINO
                GROUP BY D.ID_SOLICITACAO, F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, 
                         F.ID_PROCESSO, F.ID_ATIVIDADE, F.USUARIO_INSERCAO, F.DATA_INSERCAO, 
                         F.USUARIO_ALTERACAO, F.DATA_ALTERACAO
                ORDER BY ORDEM DESC
                LIMIT 1
            ),
            W_RESULT AS (
                SELECT F.ID_FLUXO, F.ORDEM, F.PRAZO, F.DATA_LIMITE, F.ID_PROCESSO, 
                       F.ID_ATIVIDADE, F.USUARIO_INSERCAO, F.DATA_INSERCAO, 
                       F.USUARIO_ALTERACAO, F.DATA_ALTERACAO
                FROM (
                    SELECT * FROM W_PRIMEIRO
                    UNION
                    SELECT * FROM W_EM_ANDAMEN
                ) F
                ORDER BY F.ID_SOLICITACAO ASC
            )
            SELECT * FROM W_RESULT
            """, nativeQuery = true)
    Optional<Flow> findFlowByRequestId(@Param("requestId") Integer requestId);
}