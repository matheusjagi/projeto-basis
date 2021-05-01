package com.basis.grupoum.sgt.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioListagemDTO {

    private Long id;

    private String nome;

    private String email;

    private LocalDate dataNascimento;
}
