package com.basis.grupoum.sgt.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    private String nome;

    private String email;

    private String cpf;

    private LocalDate dataNascimento;
}
