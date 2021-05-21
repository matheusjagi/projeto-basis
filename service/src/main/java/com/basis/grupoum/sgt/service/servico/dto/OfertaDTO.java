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

    @NotNull(message = "O campo não pode ser nulo")
    private Long itemDtoId;

    @NotNull(message = "O campo não pode ser nulo")
    private Long usuarioDtoId;

    private String nomeUsuarioOfertante;

    @NotNull(message = "O campo não pode ser nulo")
    private Long situacaoDtoId;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio")
    private List<ItemDTO> itensOfertados;

}
