package com.bootcamp.transacao_simplificada.infrastructure.repository;

import com.bootcamp.transacao_simplificada.infrastructure.entity.Carteira;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Transacoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacoes, Long> {
}
