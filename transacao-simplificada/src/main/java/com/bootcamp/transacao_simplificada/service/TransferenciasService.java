package com.bootcamp.transacao_simplificada.service;

import com.bootcamp.transacao_simplificada.infrastructure.controller.TransacaoDTO;
import com.bootcamp.transacao_simplificada.infrastructure.entity.TipoUsuario;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferenciasService {

    private final UsuarioService usuarioService;

    public void transferirValores( TransacaoDTO transacaoDTO ) {
        Usuario pagador = usuarioService.buscarUsuario( transacaoDTO.payer( ) );
        Usuario recebedor = usuarioService.buscarUsuario(transacaoDTO.payee( ) );

        validaPagadorLojista( pagador );
        validarSaldoUsuario( pagador, transacaoDTO.value( ) );

    }

    private void validaPagadorLojista( Usuario usuario ) {
        try{
            if( usuario.getTipoUsuario( ).equals( TipoUsuario.LOJISTA ) ) {
                throw new IllegalArgumentException( "Transacao nao autorizada para esse tipo de usuario" );
            }
        } catch ( Exception e ) {
            throw new IllegalArgumentException( e.getMessage( ) );
        }
    }

    private void validarSaldoUsuario( Usuario usuario, BigDecimal valor ) {
       try{
           if( usuario.getCarteira(  ).getSaldo(  ).compareTo( valor ) < 0 ) {
                throw new IllegalArgumentException( "Trasacao nao autorizada! Saldo insuficiente!" );
           }
       } catch ( Exception e ) {
           throw new IllegalArgumentException( e.getMessage( ) );
       }
    }


}
