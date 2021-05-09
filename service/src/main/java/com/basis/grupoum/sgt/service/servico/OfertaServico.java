package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.dominio.Situacao;
import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.repositorio.OfertaRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.EmailDTO;
import com.basis.grupoum.sgt.service.servico.dto.ItemDTO;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import com.basis.grupoum.sgt.service.servico.dto.OfertaListagemDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.ItemMapper;
import com.basis.grupoum.sgt.service.servico.mapper.OfertaListagemMapper;
import com.basis.grupoum.sgt.service.servico.mapper.OfertaMapper;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
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
    private final UsuarioServico usuarioServico;
    private final UsuarioMapper usuarioMapper;
    private final EmailServico emailServico;

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

    public OfertaDTO salvar(OfertaDTO ofertaDTO){
        ofertaDTO = alteraDisponibilidadeItensOfertados(ofertaDTO, false);
        Oferta oferta = ofertaMapper.toEntity(ofertaDTO);
        ofertaRepositorio.save(oferta);

        emailServico.sendEmail(criarEmailOferta(oferta));
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
        ItemDTO itemAuxDTO = itemServico.obterPorId(ofertaDTO.getItemDtoId());

        Long idUsuarioOfertado = itemAuxDTO.getUsuarioDtoId();

        itemAuxDTO.setUsuarioDtoId(ofertaDTO.getUsuarioDtoId());
        itemServico.atualizar(itemAuxDTO);

        ofertaDTO.getItensOfertados().forEach(itemDTO -> {
            itemDTO.setUsuarioDtoId(idUsuarioOfertado);
            itemDTO.setDisponibilidade(true);
            itemServico.atualizar(itemDTO);
        });

        Item itemCancelado = itemMapper.toEntity(itemServico.obterPorId(ofertaDTO.getItemDtoId()));
        List<OfertaDTO> ofertasCanceladas = ofertaMapper.toDto(ofertaRepositorio.findAllByItem(itemCancelado));
        cancelaDemaisOfertas(ofertasCanceladas,ofertaDTO.getItemDtoId());

        ofertaDTO.setSituacaoDtoId(2L);
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
        cancelaDemaisOfertas(ofertasCanceladas,idItem);
    }

    public OfertaDTO alteraDisponibilidadeItensOfertados(OfertaDTO ofertaDTO, boolean disponibilidade){
        ofertaDTO.getItensOfertados().forEach(itemDTO -> {
            itemDTO = itemServico.obterPorId(itemDTO.getId());
            itemDTO.setDisponibilidade(disponibilidade);
            itemServico.atualizar(itemDTO);
        });
        return ofertaDTO;
    }

    public void cancelaDemaisOfertas(List<OfertaDTO> ofertasCanceladas, Long idItemCancelado){
        ofertasCanceladas.stream().filter(ofertaDTO -> ofertaDTO.getItemDtoId().equals(idItemCancelado))
                .forEach(ofertaDTO -> {
                    ofertaDTO.setSituacaoDtoId(4l);
                    alteraDisponibilidadeItensOfertados(ofertaDTO, true);
                    atualizar(ofertaDTO);
                });
    }

    private EmailDTO criarEmailOferta(Oferta oferta){
        EmailDTO email = new EmailDTO();

        Item itemAux = itemMapper.toEntity(itemServico.obterPorId(oferta.getItem().getId()));
        Usuario usuarioAux = usuarioMapper.toEntity(usuarioServico.obterPorId(itemAux.getUsuario().getId()));

        List<String> produtosOferecidos = new ArrayList<>();

        oferta.getItensOfertados().forEach(item -> {
            item = itemMapper.toEntity(itemServico.obterPorId(item.getId()));
            produtosOferecidos.add(new String("- " + item.getNome()));
        });

        email.setAssunto("Nova oferta feita no produto: "+itemAux.getNome());
        email.setCorpo("Você recebeu uma proposta de troca para um de seus produtos disponiveis!" +
                "<br><br> Os itens oferecidos foram: <br>" + String.join("<br>",produtosOferecidos));
        email.setDestinatario(usuarioAux.getEmail());

        return email;
    }
}
