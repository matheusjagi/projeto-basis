package com.basis.grupoum.sgt.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OfertaDTO {

    private Long id;

    private Long itemDtoId;

    private Long usuarioDtoId;

    private Long situacaoDtoId;

    private List<ItemDTO> itensOfertados;

}
