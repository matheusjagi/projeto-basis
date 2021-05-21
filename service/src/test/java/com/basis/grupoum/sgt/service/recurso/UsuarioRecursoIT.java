package com.basis.grupoum.sgt.service.recurso;

import com.basis.grupoum.sgt.service.builder.UsuarioBuilder;
import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.repositorio.ItemRepositorio;
import com.basis.grupoum.sgt.service.repositorio.OfertaRepositorio;
import com.basis.grupoum.sgt.service.repositorio.UsuarioRepositorio;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioMapper;
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
public class UsuarioRecursoIT extends IntTestComum {

    private static final String URL = "/api/usuarios";

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ItemRepositorio itemRepositorio;

    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    @BeforeEach
    public void inicializar(){
        ofertaRepositorio.deleteAll();
        itemRepositorio.deleteAll();
        usuarioRepositorio.deleteAll();
        usuarioBuilder.setCustomizacao(null);
    }

    @Test
    public void autenticacao() throws Exception{
        Usuario usuario = usuarioBuilder.construir();

        getMockMvc().perform(post(URL+"/login", usuario)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void listar() throws Exception{
        usuarioBuilder.construir();
        getMockMvc().perform(get(URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void listarPorNome() throws Exception{
        Usuario usuario = usuarioBuilder.construir();
        getMockMvc().perform(get(URL+"/nome/"+usuario.getNome()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void obterPorId() throws Exception{
        Usuario usuario = usuarioBuilder.construir();

        getMockMvc().perform(get(URL+"/"+usuario.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void obterPorToken() throws Exception{
        Usuario usuario = usuarioBuilder.construir();

        getMockMvc().perform(get(URL+"/token/"+usuario.getToken())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void salvar() throws Exception{
        Usuario usuario = usuarioBuilder.construir();

        getMockMvc().perform(post(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void naoSalvaCpfDuplicado() throws Exception{
        usuarioBuilder.construir();

        getMockMvc().perform(post(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioBuilder.construirEntidade())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void atualizar() throws Exception{
        Usuario usuario = usuarioBuilder.construir();
        usuario.setNome("Usuario Alterado");

        getMockMvc().perform(put(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deletar() throws Exception{
        Usuario usuario = usuarioBuilder.construir();

        getMockMvc().perform(delete(URL+"/"+usuario.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
