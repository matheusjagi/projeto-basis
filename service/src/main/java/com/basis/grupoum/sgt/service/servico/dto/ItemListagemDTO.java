package com.basis.grupoum.sgt.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemListagemDTO
{
    private Long id;

    private String nome;

    private String descricao;

    private byte[] foto;

    private boolean disponibilidade;

    //private CategoriaDTO dtoCategoria;
}
