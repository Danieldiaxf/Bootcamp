package com.bootcamp.transacao_simplificada.infrastructure.controller;

import java.math.BigDecimal;

public record TransacaoDTO(BigDecimal value, Long payer, Long payee) {



}
