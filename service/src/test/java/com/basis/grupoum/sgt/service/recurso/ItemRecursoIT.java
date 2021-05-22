package com.basis.grupoum.sgt.service.recurso;

import com.basis.grupoum.sgt.service.builder.ItemBuilder;
import com.basis.grupoum.sgt.service.dominio.Categoria;
import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.repositorio.ItemRepositorio;
import com.basis.grupoum.sgt.service.repositorio.OfertaRepositorio;
import com.basis.grupoum.sgt.service.servico.mapper.ItemMapper;
import com.basis.grupoum.sgt.service.util.IntTestComum;
import com.basis.grupoum.sgt.service.util.TestUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@Transactional
public class ItemRecursoIT extends IntTestComum {

    private static final String URL = "/api/itens";

    @Autowired
    private ItemBuilder itemBuilder;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemRepositorio itemRepositorio;

    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    @BeforeEach
    public void inicializar(){
        ofertaRepositorio.deleteAll();
        itemRepositorio.deleteAll();
        itemBuilder.setCustomizacao(null);
    }

    @Test
    public void listar() throws Exception{
        itemBuilder.construir();

        getMockMvc().perform(get(URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void listarItensDisponiveis() throws Exception{
        Item item = itemBuilder.construir();

        getMockMvc().perform(get(URL+"/disponibilidade/"+item.isDisponibilidade()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void listarItensPorNome() throws Exception{
        Item item = itemBuilder.construir();

        getMockMvc().perform(get(URL+"/nome/"+item.getNome()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void listarItensPorCategoria() throws Exception{
        Item item = itemBuilder.construir();

        getMockMvc().perform(get(URL+"/categoria/"+item.getCategoria().getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void listarItensPorUsuario() throws Exception{
        Item item = itemBuilder.construir();

        getMockMvc().perform(get(URL+"/usuario/"+item.getUsuario().getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void obterPorId() throws Exception{
        Item item = itemBuilder.construir();
        getMockMvc().perform(get(URL+"/"+item.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemMapper.toDto(item))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void salvar() throws Exception{
        Item item = itemBuilder.construir();
        getMockMvc().perform(post(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemMapper.toDto(item))))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void atualizar() throws Exception{
        Item item = itemBuilder.construir();
        item.setNome("Item Alterado");

        getMockMvc().perform(put(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemMapper.toDto(item))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deletar() throws Exception{
        Item item = itemBuilder.construir();

        getMockMvc().perform(delete(URL+"/"+item.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemMapper.toDto(item))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
