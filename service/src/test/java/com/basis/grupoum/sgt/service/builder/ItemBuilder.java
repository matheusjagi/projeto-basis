package com.basis.grupoum.sgt.service.builder;

import com.basis.grupoum.sgt.service.dominio.Categoria;
import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.servico.ItemServico;
import com.basis.grupoum.sgt.service.servico.dto.ItemDTO;
import com.basis.grupoum.sgt.service.servico.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

@Component
public class ItemBuilder extends ConstrutorEntidade<Item> {

    @Autowired
    private ItemServico itemServico;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    @Override
    public Item construirEntidade() {
        Item item = new Item();
        item.setNome("Item teste");
        item.setDescricao("Descricao do Item teste");
        String teste = "foto";
        item.setFoto(Base64.getDecoder().decode(teste.getBytes(StandardCharsets.UTF_8)));
        item.setDisponibilidade(true);
        Categoria cat = new Categoria();
        cat.setId(1L);
        cat.setDescricao("Descricao da Categoria teste");
        item.setCategoria(cat);
        item.setUsuario(usuarioBuilder.construir());

        return item;
    }

    @Override
    public Item persistir(Item entidade) {
        ItemDTO itemDTO = itemMapper.toDto(entidade);
        return itemMapper.toEntity(itemServico.salvar(itemDTO));
    }

    @Override
    public Item construir() {
        return this.persistir(construirEntidade());
    }
}
