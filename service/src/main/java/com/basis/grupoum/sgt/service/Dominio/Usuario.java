package com.basis.grupoum.sgt.service.Dominio;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Generated;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", allocationSize = 1, sequenceName = "seq_usuario")
    @Column(name = "id")
    private long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "token")
    private String token;

    @Column(name = "dt_nascimento")
    private LocalDate dataNascimento;
}
