package com.bootcamp.transacao_simplificada.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient( url = "https://util.devi.tools/api/v1/notify", name = "noptificacao" )
public interface NotificacaoClient {

    @PostMapping
    void enviarNotificacao(  );

}
