package com.bootcamp.transacao_simplificada.service;

import com.bootcamp.transacao_simplificada.infrastructure.entity.Carteira;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Usuario;
import com.bootcamp.transacao_simplificada.infrastructure.exceptions.UserNotFound;
import com.bootcamp.transacao_simplificada.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final carteiraRepository repository;

    public void salvar( Carteira carteira ) {
        return repository.save( carteira );
    }
}
