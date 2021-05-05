package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.repositorio.OfertaRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.OfertaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OfertaServico {
    private final OfertaRepositorio ofertaRepositorio;
    private final OfertaMapper ofertaMapper;

    public List<OfertaDTO> listar (){
        List<Oferta> ofertas = ofertaRepositorio.findAll();
        return ofertaMapper.toDto(ofertas);
    }

    public OfertaDTO obterPorId(Long id){
        Oferta oferta = ofertaRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("Oferta nao encontrado"));
        return ofertaMapper.toDto(oferta);
    }

    public OfertaDTO salvar(OfertaDTO ofertaDTO){
        Oferta oferta = ofertaMapper.toEntity(ofertaDTO);
        ofertaRepositorio.save(oferta);
        return ofertaMapper.toDto(oferta);
    }

    public OfertaDTO atualizar(OfertaDTO ofertaDTO){
        Oferta oferta = ofertaMapper.toEntity(ofertaDTO);
        ofertaRepositorio.save(oferta);
        return ofertaMapper.toDto(oferta);
    }

    public void deletar(Long idOferta){
        ofertaRepositorio.deleteById(idOferta);
    }

    public void aceitaOferta(Long idOferta){

    }
}
