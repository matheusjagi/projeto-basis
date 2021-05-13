package com.basis.grupoum.sgt.service.builder;

import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.dominio.Situacao;
import com.basis.grupoum.sgt.service.servico.OfertaServico;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import com.basis.grupoum.sgt.service.servico.mapper.OfertaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collections;

@Component
public class OfertaBuilder extends ConstrutorEntidade<Oferta> {

    @Autowired
    private OfertaServico ofertaServico;

    @Autowired
    private OfertaMapper ofertaMapper;

    @Autowired
    private ItemBuilder itemBuilder;

    @Override
    public Oferta construirEntidade() {
        Oferta oferta = new Oferta();

        Item item = itemBuilder.construir();

        oferta.setItem(item);
        oferta.setUsuario(item.getUsuario());

        Situacao situacao = new Situacao();
        situacao.setId(1L);
        oferta.setSituacao(situacao);

        Item itemOfertado1 = itemBuilder.construirEntidade("19950365015","oooo@gmail.com");
        oferta.setUsuario(itemOfertado1.getUsuario());
        oferta.setItensOfertados(Collections.singletonList(itemOfertado1));

        Item itemOfertado2 = itemBuilder.construirEntidade("07224971057","egttfr@gmail.com");
        oferta.setUsuario(itemOfertado2.getUsuario());
        oferta.setItensOfertados(Collections.singletonList(itemOfertado2));

        return oferta;
    }

    @Override
    public Oferta persistir(Oferta entidade) {
        OfertaDTO ofertaDTO = ofertaMapper.toDto(entidade);
        return ofertaMapper.toEntity(ofertaServico.salvar(ofertaDTO));
    }
}
