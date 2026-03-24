package com.bootcamp.transacao_simplificada.infrastructure.configs;

import com.bootcamp.transacao_simplificada.infrastructure.entity.Carteira;
import com.bootcamp.transacao_simplificada.infrastructure.entity.TipoUsuario;
import com.bootcamp.transacao_simplificada.infrastructure.entity.Usuario;
import com.bootcamp.transacao_simplificada.infrastructure.repository.CarteiraRepository;
import com.bootcamp.transacao_simplificada.infrastructure.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class PopularTabelaUsuario {

    @Bean
    CommandLineRunner popularBanco(UsuarioRepository usuarioRepository, CarteiraRepository carteiraRepository) {
        return args -> {
            if( usuarioRepository.count( ) == 0 ) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                Usuario usuario1 = new Usuario( null,
                        "Daniel Alves",
                        "Danielalves@gmail.com",
                        "12134523302",
                        encoder.encode( "954628" ),
                        null,
                        TipoUsuario.COMUM );

                Usuario usuario2 = new Usuario( null,
                        "Mariana Souza",
                        "mariana.souza@gmail.com",
                        "98765432100",
                        encoder.encode("123456"),
                        null,
                        TipoUsuario.COMUM );

                Usuario lojista = new Usuario( null,
                        "Carlos Henrique",
                        "carlos.henrique@yahoo.com",
                        "45678912345",
                        encoder.encode("abcdef"),
                        null,
                        TipoUsuario.LOJISTA );


                usuarioRepository.saveAll(List.of( usuario1, usuario2, lojista ) );


                Carteira carteira1 = new Carteira( null, new BigDecimal( "1000.00" ), usuario1 );
                Carteira carteira2 = new Carteira( null, new BigDecimal( "2000.00" ), usuario2 );
                Carteira carteira3 = new Carteira( null, new BigDecimal( "5000.00" ), lojista );


                carteiraRepository.saveAll(List.of( carteira1, carteira2, carteira3 ) );

                System.out.println( "Usuario e Carteiras populados com sucesso" );
            }
        };

    }

}
