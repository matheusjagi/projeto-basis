package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.dominio.Oferta;
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
        List<Oferta> ofertasPorSitucao = ofertaRepositorio.findAllBySituacaoId(idSituacao);
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

    public void atualizarTodas(List<OfertaDTO> ofertas){
        List<Oferta> ofertasAtualizaveis = ofertaMapper.toEntity(ofertas);
        ofertaRepositorio.saveAll(ofertasAtualizaveis);
    }

    public void deletar(Long idOferta){
        ofertaRepositorio.deleteById(idOferta);
    }

    public void aceitar(Long idOferta){
        OfertaDTO oferta = obterPorId(idOferta);
        ItemDTO itemQueRecebeuAOferta = itemServico.obterPorId(oferta.getItemDtoId());
        List<ItemDTO> itensOfertados = oferta.getItensOfertados();
        itensOfertados.forEach(item -> item.setUsuarioDtoId(itemQueRecebeuAOferta.getUsuarioDtoId()));
        itemQueRecebeuAOferta.setUsuarioDtoId(oferta.getUsuarioDtoId());

        itemServico.salvar(itemQueRecebeuAOferta);
        itemServico.atualizarTodos(itensOfertados);
        oferta.setSituacaoDtoId(2L);
        atualizar(oferta);
    }

    public void recusar(Long idOferta){
        OfertaDTO ofertaDTO = obterPorId(idOferta);
        ofertaDTO.setSituacaoDtoId(3L);
        ofertaDTO = alteraDisponibilidadeItensOfertados(ofertaDTO, true);
        atualizar(ofertaDTO);
    }

    public void cancelar(Long idItem){
        List<OfertaDTO> ofertasCanceladas = ofertaMapper.toDto(ofertaRepositorio.findAllByItemId(idItem));
        cancelaDemaisOfertas(ofertasCanceladas,idItem);
    }

    public OfertaDTO alteraDisponibilidadeItensOfertados(OfertaDTO ofertaDTO, boolean disponibilidade){
        List<ItemDTO> itens = ofertaDTO.getItensOfertados();
        List<Long> itensPorId = new ArrayList<>();
        List<ItemDTO> itensCompletos = new ArrayList<>();

        itens.forEach(itemDTO -> {
            itensPorId.add(itemDTO.getId());
        });

        itens = itemServico.obterPorTodosId(itensPorId);

        itens.forEach(itemDTO -> {
            itemDTO.setDisponibilidade(disponibilidade);
            itensCompletos.add(itemDTO);
        });

        itemServico.atualizarTodos(itensCompletos);
        return ofertaDTO;
    }

    public void cancelaDemaisOfertas(List<OfertaDTO> ofertasCanceladas, Long idItemCancelado){
        ofertasCanceladas.stream().filter(ofertaDTO -> ofertaDTO.getItemDtoId().equals(idItemCancelado))
                .forEach(ofertaDTO -> {
                    ofertaDTO.setSituacaoDtoId(4L);
                    alteraDisponibilidadeItensOfertados(ofertaDTO, true);
                });
        atualizarTodas(ofertasCanceladas);
    }

    private EmailDTO criarEmailOferta(Oferta oferta){
        EmailDTO email = new EmailDTO();

        Item itemAux = itemMapper.toEntity(itemServico.obterPorId(oferta.getItem().getId()));
        Usuario usuarioAux = usuarioMapper.toEntity(usuarioServico.getById(itemAux.getUsuario().getId()));

        List<String> produtosOferecidos = new ArrayList<>();

        oferta.getItensOfertados().forEach(item -> {
            item = itemMapper.toEntity(itemServico.obterPorId(item.getId()));
            produtosOferecidos.add("- " + item.getNome());
        });

        email.setAssunto("Nova oferta feita no produto: "+itemAux.getNome());
        email.setCorpo("Você recebeu uma proposta de troca para um de seus produtos disponiveis!" +
                "<br><br> Os itens oferecidos foram: <br>" + String.join("<br>",produtosOferecidos));
        email.setDestinatario(usuarioAux.getEmail());

        return email;
    }
}
