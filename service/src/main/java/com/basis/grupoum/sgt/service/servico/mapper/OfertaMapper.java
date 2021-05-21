package com.basis.grupoum.sgt.service.servico.mapper;

import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface OfertaMapper extends EntityMapper<OfertaDTO, Oferta> {
    @Override
    @Mapping(source = "itemDtoId", target = "item.id")
    @Mapping(source = "usuarioDtoId", target = "usuario.id")
    @Mapping(source = "nomeUsuarioOfertante", target = "usuario.nome")
    @Mapping(source = "situacaoDtoId", target = "situacao.id")
    Oferta toEntity(OfertaDTO dto);

    @Override
    @InheritInverseConfiguration
    OfertaDTO toDto(Oferta entity);
}
