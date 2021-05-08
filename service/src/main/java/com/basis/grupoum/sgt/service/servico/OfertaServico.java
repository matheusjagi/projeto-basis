package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Categoria;
import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.dominio.Situacao;
import com.basis.grupoum.sgt.service.repositorio.CategoriaRepositorio;
import com.basis.grupoum.sgt.service.repositorio.OfertaRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.EmailDTO;
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
    private final ItemServico itemServico;
    private final ItemMapper itemMapper;
    private final CategoriaRepositorio categoriaRepositorio;

    public List<OfertaListagemDTO> listar(){
        List<Oferta> ofertas = ofertaRepositorio.findAll();
        return ofertaListagemMapper.toDto(ofertas);
    }

    public List<OfertaListagemDTO> listarPorSitucao(Long idSituacao){
        Situacao situacao = new Situacao();
        situacao.setId(idSituacao);
        List<Oferta> ofertasPorSitucao = ofertaRepositorio.findAllBySituacao(situacao);
        return ofertaListagemMapper.toDto(ofertasPorSitucao);
    }

    public OfertaDTO obterPorId(Long id){
        Oferta oferta = ofertaRepositorio
                .findById(id).orElseThrow(() -> new RegraNegocioException("Oferta não encontrada"));
        return ofertaMapper.toDto(oferta);
    }

    public Categoria obterCategoriaPorId(Long id){
        Categoria categoria = categoriaRepositorio
                .findById(id).orElseThrow(() -> new RegraNegocioException("Categoria não encontrada"));
        return categoria;
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

        ofertaDTO.setSituacaoDtoId(2L);

        Long idUsuarioOfertante = ofertaDTO.getItensOfertados().get(0).getUsuarioDtoId();

        Oferta oferta = ofertaMapper.toEntity(ofertaDTO);
        ItemDTO itemAuxDTO = itemServico.obterPorId(oferta.getItem().getId());
        itemAuxDTO.setUsuarioDtoId(idUsuarioOfertante);
        itemServico.atualizar(itemAuxDTO);

        alteraDisponibilidadeItensOfertados(ofertaDTO, true);
        ofertaDTO.getItensOfertados().forEach(itemDTO -> {
            itemDTO.setUsuarioDtoId(ofertaDTO.getUsuarioDtoId());
            itemServico.atualizar(itemDTO);
        });

        atualizar(ofertaDTO);
    }

    public void recusar(Long idOferta){
        OfertaDTO ofertaDTO = obterPorId(idOferta);
        ofertaDTO.setSituacaoDtoId(3L);
        ofertaDTO = alteraDisponibilidadeItensOfertados(ofertaDTO, true);
        atualizar(ofertaDTO);
    }

    public void cancelar(Long idItem){
        Item itemCancelado = itemMapper.toEntity(itemServico.obterPorId(idItem));

        List<OfertaDTO> ofertasCanceladas = ofertaMapper.toDto(ofertaRepositorio.findAllByItem(itemCancelado));

        ofertasCanceladas.stream().filter(ofertaDTO -> ofertaDTO.getItemDtoId().equals(idItem))
                .forEach(ofertaDTO -> {
                    ofertaDTO.setSituacaoDtoId(4L);
                    alteraDisponibilidadeItensOfertados(ofertaDTO, true);
                    atualizar(ofertaDTO);
                });
    }

    public OfertaDTO alteraDisponibilidadeItensOfertados(OfertaDTO ofertaDTO, boolean disponibilidade){
        ofertaDTO.getItensOfertados().forEach(itemDTO -> {
            itemDTO = itemServico.obterPorId(itemDTO.getId());
            itemDTO.setDisponibilidade(disponibilidade);
            itemServico.atualizar(itemDTO);
        });
        return ofertaDTO;
    }

    //Implementar
    private EmailDTO criarEmailOferta(Oferta oferta){
        EmailDTO email = new EmailDTO();

        //email.setAssunto("Cadastro de Usuario");
        //email.setCorpo("Você se cadastrou no SGTPM! Seu TOKEN de acesso é: "+usuario.getToken());
        //email.setDestinatario(usuario.getEmail());

        return email;
    }
}
