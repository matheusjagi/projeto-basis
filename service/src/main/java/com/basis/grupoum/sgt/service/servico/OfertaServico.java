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
        ofertaDTO = alteraDisponibilidadeItensOfertados(ofertaDTO, false);
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

        ofertaDTO.setSituacaoDtoId(Long.valueOf(2));

        Long idUsuarioOfertante = ofertaDTO.getItensOfertados().get(0).getUsuarioDtoId();

        Oferta oferta = ofertaMapper.toEntity(ofertaDTO);
        ItemDTO itemAuxDTO = itemMapper.toDto(oferta.getItem());
        itemAuxDTO.setUsuarioDtoId(idUsuarioOfertante);
        itemRecurso.atualizar(itemAuxDTO);

        alteraDisponibilidadeItensOfertados(ofertaDTO, true);
        ofertaDTO.getItensOfertados().forEach(itemDTO -> {
            itemDTO.setUsuarioDtoId(ofertaDTO.getUsuarioDtoId());
            itemRecurso.atualizar(itemDTO);
        });

        atualizar(ofertaDTO);
    }

    public void recusar(Long idOferta){
        OfertaDTO ofertaDTO = obterPorId(idOferta);
        ofertaDTO.setSituacaoDtoId(Long.valueOf(3));
        atualizar(ofertaDTO);
    }

    public void cancelar(Long idOferta){
        OfertaDTO ofertaCancelada = obterPorId(idOferta);

        List<OfertaDTO> ofertas = ofertaMapper.toDto(ofertaRepositorio.findAll());

        ofertas.stream().filter(ofertaDTO -> ofertaDTO.getItemDtoId() == ofertaCancelada.getItemDtoId())
                .forEach(ofertaDTO -> {
                    ofertaDTO.setSituacaoDtoId(Long.valueOf(4));
                    alteraDisponibilidadeItensOfertados(ofertaDTO, true);
                });
    }

    public OfertaDTO alteraDisponibilidadeItensOfertados(OfertaDTO ofertaDTO, boolean disponibilidade){
        ofertaDTO.getItensOfertados().forEach(
                itemDTO -> itemDTO.setDisponibilidade(disponibilidade));
        return ofertaDTO;
    }
}
