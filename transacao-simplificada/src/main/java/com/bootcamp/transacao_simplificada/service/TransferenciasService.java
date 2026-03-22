package com.bootcamp.transacao_simplificada.service;

import com.bootcamp.transacao_simplificada.infrastructure.controller.TransacaoDTO;
import com.bootcamp.transacao_simplificada.infrastructure.entity.TipoUsuario;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferenciasService {

    private final UsuarioService usuarioService;

    public void transferirValores( TransacaoDTO transacaoDTO ) {
        Usuario pagador = usuarioService.buscarUsuario( transacaoDTO.payer( ) );
        Usuario recebedor = usuarioService.buscarUsuario(transacaoDTO.payee( ) );

        validaPagadorLojista( pagador );

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

}
