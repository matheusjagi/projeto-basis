package com.basis.grupoum.sgt.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OfertaListagemDTO {

    private Long id;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo está vazio")
    private String nomeItem;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo está vazio")
    private String descricaoItem;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo está vazio")
    private byte[] foto;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo está vazio")
    private String nomeUsuario;

    @NotNull(message = "O campo não pode ser nulo")
    private Long situacaoDtoId;
}
