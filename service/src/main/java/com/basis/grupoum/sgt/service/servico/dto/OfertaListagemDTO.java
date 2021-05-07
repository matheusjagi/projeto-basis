package com.basis.grupoum.sgt.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfertaListagemDTO {

    private Long idOferta;

    private String nomeItem;

    private String descricaoItem;

    private byte[] foto;

    private String nomeUsuario;
}
