package com.basis.grupoum.sgt.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemDTO {

    private Long id;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo não pode ser vazio")
    private String nome;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo não pode ser vazio")
    private String descricao;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo não pode ser vazio")
    private byte[] foto;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo não pode ser vazio")
    private boolean disponibilidade;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo não pode ser vazio")
    private Long usuarioDtoId;

    private String nomeUsuarioProprietario;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo não pode ser vazio")
    private Long categoriaDtoId;
}
