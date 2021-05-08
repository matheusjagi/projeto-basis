package com.basis.grupoum.sgt.service.builder;

import com.basis.grupoum.sgt.service.dominio.Categoria;
import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.dominio.Situacao;
import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.servico.ItemServico;
import com.basis.grupoum.sgt.service.servico.OfertaServico;
import com.basis.grupoum.sgt.service.servico.UsuarioServico;
import com.basis.grupoum.sgt.service.servico.dto.ItemDTO;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import com.basis.grupoum.sgt.service.servico.mapper.ItemMapper;
import com.basis.grupoum.sgt.service.servico.mapper.OfertaMapper;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Component
public class OfertaBuilder extends ConstrutorEntidade<Oferta> {

    @Autowired
    private OfertaServico ofertaServico;

    @Autowired
    private OfertaMapper ofertaMapper;

    @Autowired
    private ItemBuilder itemBuilder;

    @Autowired
    private ItemServico itemServico;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public Oferta construirEntidade() {
        Oferta oferta = new Oferta();

        Item item = itemBuilder.construir();

        oferta.setItem(item);
        oferta.setUsuario(item.getUsuario());

        Situacao situacao = new Situacao();
        situacao.setId(1L);
        oferta.setSituacao(situacao);

        Item itemOfertado = itemBuilder.construirEntidade("19950365015","oooo@gmail.com");

        oferta.setUsuario(itemOfertado.getUsuario());

        oferta.setItensOfertados(Collections.singletonList(itemOfertado));

        //Item item3 = itemBuilder.construirEntidade("09231805088","deguy@gmail.com");

        //oferta.getItensOfertados().add(item3);

        return oferta;
    }

    @Override
    public Oferta persistir(Oferta entidade) {
        OfertaDTO ofertaDTO = ofertaMapper.toDto(entidade);
        return ofertaMapper.toEntity(ofertaServico.salvar(ofertaDTO));
    }
}
