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
import java.util.Base64;
import java.util.List;

@Component
public class ItemBuilder extends ConstrutorEntidade<Item> {

    @Autowired
    private ItemServico itemServico;

    @Autowired
    private CategoriaServico categoriaServico;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    public List<Categoria> buscarCategorias(){
        return categoriaServico.buscarTodasCategorias();
    }

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
        cat.setDescricao("TESTE");
        item.setCategoria(cat);
        Usuario usuario = usuarioBuilder.construir();
        item.setUsuario(usuario);
        return item;
    }

    public Item construirEntidade(String cpfUsuario, String emailUsuario) {
        Item item = new Item();
        item.setNome("Item teste");
        item.setDescricao("Descricao do Item teste");
        String teste = "foto";
        item.setFoto(Base64.getDecoder().decode(teste.getBytes(StandardCharsets.UTF_8)));
        item.setDisponibilidade(true);
        Categoria cat = new Categoria();
        cat.setId(1L);
        item.setCategoria(cat);
        item.setUsuario(usuarioBuilder.construirEntidade(cpfUsuario,emailUsuario));
        return persistir(item);
    }

    @Override
    public Item persistir(Item entidade) {
        ItemDTO itemDTO = itemMapper.toDto(entidade);
        return itemMapper.toEntity(itemServico.salvar(itemDTO));
    }
}
