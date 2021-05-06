package com.basis.grupoum.sgt.service.recurso;

import com.basis.grupoum.sgt.service.builder.UsuarioBuilder;
import com.basis.grupoum.sgt.service.dominio.Usuario;
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

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @BeforeEach
    public void inicializar(){
        usuarioRepositorio.deleteAll();
        usuarioBuilder.setCustomizacao(null);
    }

    @Test
    public void listar() throws Exception{
        usuarioBuilder.construir();
        getMockMvc().perform(get("/api/usuarios"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void salvar() throws Exception{
        Usuario usuario = usuarioBuilder.construirEntidade();
        getMockMvc().perform(post("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void atualizar() throws Exception{
        Usuario usuario = usuarioBuilder.construir();
        usuario.setNome("Usuario Alterado");

        getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deletar() throws Exception{
        Usuario usuario = usuarioBuilder.construir();

        getMockMvc().perform(delete("/api/usuarios/"+usuario.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
