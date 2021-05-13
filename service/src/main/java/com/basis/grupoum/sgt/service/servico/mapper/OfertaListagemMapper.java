package com.basis.grupoum.sgt.service.servico.mapper;

import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.servico.dto.OfertaListagemDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface OfertaListagemMapper extends EntityMapper<OfertaListagemDTO, Oferta> {

    @Override
    @Mapping(source = "nomeItem", target = "item.nome")
    @Mapping(source = "descricaoItem", target = "item.descricao")
    @Mapping(source = "foto", target = "item.foto")
    @Mapping(source = "nomeUsuario", target = "usuario.nome")
    @Mapping(source = "situacaoDtoId", target = "situacao.id")
    Oferta toEntity(OfertaListagemDTO dto);

    @Override
    @InheritInverseConfiguration
    OfertaListagemDTO toDto(Oferta entity);
}
