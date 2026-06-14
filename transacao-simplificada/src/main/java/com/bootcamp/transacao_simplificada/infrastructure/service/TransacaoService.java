package com.bootcamp.transacao_simplificada.infrastructure.service;

import com.bootcamp.transacao_simplificada.infrastructure.controller.TransacaoDTO;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Carteira;
import com.bootcamp.transacao_simplificada.infrastructure.entity.TipoUsuario;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Transacoes;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Usuario;
import com.bootcamp.transacao_simplificada.infrastructure.exceptions.BadRequestException;
import com.bootcamp.transacao_simplificada.infrastructure.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransacaoService {


    private static final BigDecimal LIMITE_DIARIO = new BigDecimal("1000.00");

    private final UsuarioService usuarioService;
    private final AutorizacaoService autorizacaoService;
    private final CarteiraService carteiraService;
    private final TransacaoRepository repository;
    private final NotificacaoService notificacaoService;

    @Transactional
    public void transferirValores(TransacaoDTO transacaoDTO) {
        Usuario pagador = usuarioService.buscarUsuario(transacaoDTO.payer());
        Usuario recebedor = usuarioService.buscarUsuario(transacaoDTO.payee());

        validaPagadorLojista(pagador);
        validarSaldoUsuario(pagador, transacaoDTO.value());
        validarLimiteDiario(pagador, transacaoDTO.value());
        validarTransferencia();

        pagador.getCarteira().setSaldo(pagador.getCarteira().getSaldo().subtract(transacaoDTO.value()));
        atualizarSaldoCarteira(pagador.getCarteira());

        recebedor.getCarteira().setSaldo(recebedor.getCarteira().getSaldo().add(transacaoDTO.value()));
        atualizarSaldoCarteira(recebedor.getCarteira());

        Transacoes transacoes = Transacoes.builder()
                .valor(transacaoDTO.value())
                .pagador(pagador)
                .recebedor(recebedor)
                .build();
        repository.save(transacoes);
        enviarNotificacao();
    }

    private void validarLimiteDiario(Usuario pagador, BigDecimal valor) {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        BigDecimal totalDoDia = repository.somarTransacoesDoDia(pagador, inicioDia);

        if (totalDoDia.add(valor).compareTo(LIMITE_DIARIO) > 0) {
            BigDecimal limiteRestante = LIMITE_DIARIO.subtract(totalDoDia);
            throw new IllegalArgumentException(
                    "Limite diário de transferência atingido. Limite restante: R$ " + limiteRestante
            );
        }
    }

    private void validaPagadorLojista(Usuario usuario) {
        try {
            if (usuario.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
                throw new IllegalArgumentException("Transação não autorizada para esse tipo de usuario");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validarSaldoUsuario(Usuario usuario, BigDecimal valor) {
        try {
            if (usuario.getCarteira().getSaldo().compareTo(valor) < 0) {
                throw new IllegalArgumentException("Transação não autorizada, saldo insuficiente");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validarTransferencia() {
        try {
            if (!autorizacaoService.validarTransferencia()) {
                throw new IllegalArgumentException("Transação não autorizada pela api");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void atualizarSaldoCarteira(Carteira carteira) {
        carteiraService.salvar(carteira);
    }

    private void enviarNotificacao() {
        try {
            notificacaoService.enviarNotificacao();
        } catch (HttpClientErrorException e) {
            throw new BadRequestException("Erro ao enviar notificacao");
        }
    }

    private void validarValorPositivo(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser maior que zero");
        }
    }
}