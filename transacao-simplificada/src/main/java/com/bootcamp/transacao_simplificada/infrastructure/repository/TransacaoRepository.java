package com.bootcamp.transacao_simplificada.infrastructure.repository;

import com.bootcamp.transacao_simplificada.infrastructure.entity.Transacoes;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransacaoRepository extends JpaRepository<Transacoes, Long> {

    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM transacao t " +
            "WHERE t.pagador = :pagador AND t.dataHoraTransacao >= :inicioDia")
    BigDecimal somarTransacoesDoDia(@Param("pagador") Usuario pagador,
                                    @Param("inicioDia") LocalDateTime inicioDia);
}