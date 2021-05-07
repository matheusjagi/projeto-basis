package com.basis.grupoum.sgt.service.builder;

import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.dominio.Situacao;
import com.basis.grupoum.sgt.service.servico.OfertaServico;
import com.basis.grupoum.sgt.service.servico.dto.OfertaDTO;
import com.basis.grupoum.sgt.service.servico.mapper.OfertaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        Item item = itemBuilder.construirEntidade();

        oferta.setUsuario(item.getUsuario());
        oferta.setItem(item);

        Situacao situacao = new Situacao();
        situacao.setDescricao("Descricao da Situacao teste");
        oferta.setSituacao(situacao);

        Item itemOfertado1 = itemBuilder.construirEntidade();
        itemOfertado1.getUsuario().setNome("Usuario Ofertante teste");
        itemOfertado1.setNome("Item OFERTADO 1");
        itemOfertado1.getUsuario().setCpf("72653173000");

        Item itemOfertado2 = itemBuilder.construirEntidade();
        itemOfertado2.getUsuario().setNome("Usuario Ofertante teste");
        itemOfertado2.setNome("Item OFERTADO 2");
        itemOfertado2.getUsuario().setCpf("72653173000");

        oferta.getItensOfertados().add(itemOfertado1);
        oferta.getItensOfertados().add(itemOfertado2);

        return oferta;
    }

    @Override
    public Oferta persistir(Oferta entidade) {
        OfertaDTO ofertaDTO = ofertaMapper.toDto(entidade);
        return ofertaMapper.toEntity(ofertaServico.salvar(ofertaDTO));
    }

    @Override
    public Oferta construir() {
        return this.persistir(construirEntidade());
    }

}
