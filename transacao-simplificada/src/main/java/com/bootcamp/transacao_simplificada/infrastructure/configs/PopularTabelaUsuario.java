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

                Usuario usuario3 = new Usuario( null,
                        "Fernanda Lima",
                        "fernanda.lima@gmail.com",
                        "32165498700",
                        encoder.encode("fern@321"),
                        null,
                        TipoUsuario.COMUM );

                Usuario usuario4 = new Usuario( null,
                        "Ricardo Mendes",
                        "ricardo.mendes@hotmail.com",
                        "65432198711",
                        encoder.encode("ric#456"),
                        null,
                        TipoUsuario.COMUM );

                Usuario usuario5 = new Usuario( null,
                        "Juliana Costa",
                        "juliana.costa@outlook.com",
                        "78901234522",
                        encoder.encode("juli789"),
                        null,
                        TipoUsuario.COMUM );

                Usuario usuario6 = new Usuario( null,
                        "Bruno Nascimento",
                        "bruno.nascimento@gmail.com",
                        "11223344533",
                        encoder.encode("brun@001"),
                        null,
                        TipoUsuario.COMUM );

                Usuario lojista2 = new Usuario( null,
                        "Padaria Pão & Arte",
                        "contato@paoearte.com.br",
                        "12345678000144",
                        encoder.encode("pao@2024"),
                        null,
                        TipoUsuario.LOJISTA );

                Usuario lojista3 = new Usuario( null,
                        "Tech Store LTDA",
                        "financeiro@techstore.com.br",
                        "98765432000188",
                        encoder.encode("tech#store"),
                        null,
                        TipoUsuario.LOJISTA );


                usuarioRepository.saveAll(List.of
                        (
                            usuario1, usuario2, lojista,
                            usuario3, usuario4, usuario5, usuario6,
                            lojista2, lojista3
                        ));


                Carteira carteira1 = new Carteira( null, new BigDecimal( "1000.00" ), usuario1 );
                Carteira carteira2 = new Carteira( null, new BigDecimal( "2000.00" ), usuario2 );
                Carteira carteira3 = new Carteira( null, new BigDecimal( "5000.00" ), lojista );
                Carteira carteira4 = new Carteira( null, new BigDecimal( "750.50" ), usuario3 );
                Carteira carteira5 = new Carteira( null, new BigDecimal( "150.00" ), usuario4 );
                Carteira carteira6 = new Carteira( null, new BigDecimal( "3200.00" ), usuario5 );
                Carteira carteira7 = new Carteira( null, new BigDecimal( "0.00" ), usuario6 );
                Carteira carteira8 = new Carteira( null, new BigDecimal( "8000.00" ),lojista2 );
                Carteira carteira9 = new Carteira( null, new BigDecimal( "15000.00" ),lojista3 );


                carteiraRepository.saveAll(List.of(
                        carteira1, carteira2, carteira3,
                        carteira4, carteira5, carteira6,
                        carteira7, carteira8, carteira9
                ));

                System.out.println( "Usuario e Carteiras populados com sucesso" );
            }
        };

    }

}
