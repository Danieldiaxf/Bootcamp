package com.bootcamp.transacao_simplificada.service;

import com.bootcamp.transacao_simplificada.infrastructure.clients.AutorizacaoClient;
import com.bootcamp.transacao_simplificada.infrastructure.clients.NotificacaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoClient client;

    public void enviarNotificacao(){
        client.enviarNotificacao();
    }
}