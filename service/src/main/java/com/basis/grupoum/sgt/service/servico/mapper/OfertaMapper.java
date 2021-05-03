package com.basis.grupoum.sgt.service.servico.mapper;

import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface OfertaMapper extends EntityMapper<OfertaDTO, Oferta> {
    @Override
    @Mapping(source = "itemDtoId", target = "item.id")
    @Mapping(source = "usuarioDtoId", target = "usuario.id")
    @Mapping(source = "situacaoDtoId", target = "situacao.id")
    Oferta toEntity(OfertaDTO dto);

    @Override
    @Mapping(source = "item.id", target = "itemDtoId")
    @Mapping(source = "usuario.id", target = "usuarioDtoId")
    @Mapping(source = "situacao.id", target = "situacaoDtoId")
    OfertaDTO toDto(Oferta entity);
}
