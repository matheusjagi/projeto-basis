package com.basis.grupoum.sgt.service.servico.dto;

import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.dominio.Situacao;
import com.basis.grupoum.sgt.service.dominio.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OfertaDTO {

    private Long id;

    private Item item;

    private Usuario usuario;

    private Situacao situacao;

    private List<Item> itensOfertados;

}
