package com.bootcamp.transacao_simplificada.infrastructure.repository;

import com.bootcamp.transacao_simplificada.infrastructure.entity.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
