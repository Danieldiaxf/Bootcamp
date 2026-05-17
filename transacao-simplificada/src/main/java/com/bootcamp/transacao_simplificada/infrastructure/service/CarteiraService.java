package com.bootcamp.transacao_simplificada.infrastructure.service;

import com.bootcamp.transacao_simplificada.infrastructure.entity.Carteira;
import com.bootcamp.transacao_simplificada.infrastructure.repository.CarteiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository repository;

    public void salvar(Carteira carteira){
        repository.save(carteira);
    }
}
