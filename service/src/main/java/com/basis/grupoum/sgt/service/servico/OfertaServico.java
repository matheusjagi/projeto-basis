package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.recurso.ItemRecurso;
import com.basis.grupoum.sgt.service.repositorio.OfertaRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.ItemDTO;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import com.basis.grupoum.sgt.service.servico.dto.OfertaListagemDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.ItemMapper;
import com.basis.grupoum.sgt.service.servico.mapper.OfertaListagemMapper;
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
    private final OfertaListagemMapper ofertaListagemMapper;
    private final ItemRecurso itemRecurso;
    private final ItemMapper itemMapper;

    public List<OfertaListagemDTO> listar(){
        List<Oferta> ofertas = ofertaRepositorio.findAll();
        return ofertaListagemMapper.toDto(ofertas);
    }

    public List<OfertaListagemDTO> listarPorSitucao(Long idSituacao){
        List<OfertaListagemDTO> ofertasPorSitucao = ofertaRepositorio.findAllBySituacao(idSituacao);
        return ofertasPorSitucao;
    }

    public OfertaDTO obterPorId(Long id){
        Oferta oferta = ofertaRepositorio
                .findById(id).orElseThrow(() -> new RegraNegocioException("Oferta não encontrada"));
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

    public void aceitar(Long idOferta){
        OfertaDTO ofertaDTO = obterPorId(idOferta);

        //Setar a SITUAÇÃO como APROVADA
        ofertaDTO.setSituacaoDtoId(Long.valueOf(2));

        //Pegar o ID do Usuario ofertante
        Long idUsuarioOfertante = ofertaDTO.getItensOfertados().get(0).getUsuarioDtoId();

        //Joga o ITEM da Oferta para o USUARIO ofertante
        Oferta oferta = ofertaMapper.toEntity(ofertaDTO);
        ItemDTO itemAuxDTO = itemMapper.toDto(oferta.getItem());
        itemAuxDTO.setUsuarioDtoId(idUsuarioOfertante);
        itemRecurso.atualizar(itemAuxDTO);

        //Joga todos os ITENS oferecidos na OFERTA para o USUARIO da oferta
        for(ItemDTO itemDTO: ofertaDTO.getItensOfertados()){
            itemDTO.setUsuarioDtoId(ofertaDTO.getUsuarioDtoId());
            itemRecurso.atualizar(itemDTO);
        }

        //Salvar oferta
        atualizar(ofertaDTO);
    }

    public void recusar(Long idOferta){
        OfertaDTO ofertaDTO = obterPorId(idOferta);
        ofertaDTO.setSituacaoDtoId(Long.valueOf(3));
        atualizar(ofertaDTO);
    }
}
