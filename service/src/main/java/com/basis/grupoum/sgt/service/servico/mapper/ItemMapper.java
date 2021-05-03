package com.basis.grupoum.sgt.service.servico.mapper;

import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.servico.dto.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {
    @Override
    @Mapping(source = "usuarioDtoId", target = "usuario.id")
    @Mapping(source = "categoriaDtoId", target = "categoria.id")
    Item toEntity(ItemDTO dto);

    @Override
    @Mapping(source = "usuario.id", target = "usuarioDtoId")
    @Mapping(source = "categoria.id", target = "categoriaDtoId")
    ItemDTO toDto(Item entity);
}
