package com.bootcamp.transacao_simplificada.service;

import com.bootcamp.transacao_simplificada.infrastructure.clients.AutorizacaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AutorizacaoService {

    private final AutorizacaoClient client;

    public boolean validarTransferencia(  ) {

        if (Objects.equals( client.validarAutorizacao( ).data( ).autorization( ), "true" ) ) {
            return true;
        }
        return false;
    }
}
