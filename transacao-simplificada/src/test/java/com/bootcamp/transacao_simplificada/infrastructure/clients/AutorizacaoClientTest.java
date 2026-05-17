package com.bootcamp.transacao_simplificada.infrastructure.clients;

import com.bootcamp.transacao_simplificada.TransacaoSimplificadaApplication;
import com.bootcamp.transacao_simplificada.infrastructure.service.AutorizacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TransacaoSimplificadaApplication.class)
@ActiveProfiles("test")
class AutorizacaoClientTest {

    // MockBean substitui o Feign por um mock controlado pelo Mockito
    // Não faz chamada HTTP real — totalmente isolado
    @MockBean
    private AutorizacaoClient autorizacaoClient;

    @Autowired
    private AutorizacaoService autorizacaoService;

    // ─────────────────────────────────────────────────────────────
    // Cenário 1: API retorna authorization = true
    // Esperado: validarTransferencia() retorna true
    // ─────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Deve retornar true quando API externa autoriza a transação")
    void deveRetornarTrueQuandoApiAutoriza() {

        AutorizacaoDTO respostaMock = new AutorizacaoDTO("success", new DataDTO("true"));
        when(autorizacaoClient.validarAutorizacao()).thenReturn(respostaMock);

        boolean resultado = autorizacaoService.validarTransferencia();

        assertTrue(resultado);
    }

    // ─────────────────────────────────────────────────────────────
    // Cenário 2: API retorna authorization = false
    // Esperado: validarTransferencia() retorna false
    // ─────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Deve retornar false quando API externa nega a transação")
    void deveRetornarFalseQuandoApiNega() {

        AutorizacaoDTO respostaMock = new AutorizacaoDTO("fail", new DataDTO("false"));
        when(autorizacaoClient.validarAutorizacao()).thenReturn(respostaMock);

        boolean resultado = autorizacaoService.validarTransferencia();

        assertFalse(resultado);
    }

    // ─────────────────────────────────────────────────────────────
    // Cenário 3: API lança exceção (serviço indisponível)
    // Esperado: exceção propagada
    // ─────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Deve lançar exceção quando API externa está indisponível")
    void deveLancarExcecaoQuandoApiIndisponivel() {

        when(autorizacaoClient.validarAutorizacao())
                .thenThrow(new RuntimeException("Serviço externo indisponível"));

        assertThrows(RuntimeException.class,
                () -> autorizacaoService.validarTransferencia());
    }
}