package com.bootcamp.transacao_simplificada.infrastructure.controller;

import com.bootcamp.transacao_simplificada.infrastructure.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Void> realizarTransacao(@RequestBody TransacaoDTO transacaoDTO){
        transacaoService.transferirValores(transacaoDTO);
        return ResponseEntity.accepted().build();
    }
}