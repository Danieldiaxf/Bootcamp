package com.bootcamp.transacao_simplificada.infrastructure.repository;

import com.bootcamp.transacao_simplificada.infrastructure.entity.Carteira;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
