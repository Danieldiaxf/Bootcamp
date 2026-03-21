package com.bootcamp.transacao_simplificada.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity( name = "carteira" )
@Table
public class Carteira {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private BigDecimal saldo;

    @JoinColumn( name = "usuario_id" )
    @OneToOne
    private Usuario usuario;
}
