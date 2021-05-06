package com.basis.grupoum.sgt.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OfertaDTO {

    private Long id;

    private Long itemDtoId;

    private Long usuarioDtoId;

    private Long situacaoDtoId;

    @NotBlank
    private List<ItemDTO> itensOfertados;

}
