package com.basis.grupoum.sgt.service.servico.mapper;

import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfertaMapper extends EntityMapper<OfertaDTO, Oferta> {
}
