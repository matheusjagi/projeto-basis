package com.basis.grupoum.sgt.service.recurso;

import com.basis.grupoum.sgt.service.builder.OfertaBuilder;
import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.repositorio.OfertaRepositorio;
import com.basis.grupoum.sgt.service.servico.mapper.OfertaMapper;
import com.basis.grupoum.sgt.service.util.IntTestComum;
import com.basis.grupoum.sgt.service.util.TestUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@Transactional
public class OfertaRecursoIT extends IntTestComum {

    private static final String URL = "/api/ofertas";

    @Autowired
    private OfertaBuilder ofertaBuilder;

    @Autowired
    private OfertaMapper ofertaMapper;

    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    @BeforeEach
    public void inicializar(){
        ofertaRepositorio.deleteAll();
        ofertaBuilder.setCustomizacao(null);
    }

    @Test
    public void listar() throws Exception{
        ofertaBuilder.construir();
        getMockMvc().perform(get(URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void listarPorSitucao() throws Exception{
        Oferta oferta = ofertaBuilder.construir();

        getMockMvc().perform(get(URL+"/situacao/"+oferta.getSituacao().getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void listarPorUsuario() throws Exception{
        Oferta oferta = ofertaBuilder.construir();

        getMockMvc().perform(get(URL+"/usuario/"+oferta.getUsuario().getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void obterOfertasPorItem() throws Exception{
        Oferta oferta = ofertaBuilder.construir();

        getMockMvc().perform(get(URL+"/item/"+oferta.getItem().getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void obterPorId() throws Exception{
        Oferta oferta = ofertaBuilder.construir();

        getMockMvc().perform(get(URL+"/"+oferta.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ofertaMapper.toDto(oferta))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void salvar() throws Exception{
        Oferta oferta = ofertaBuilder.construir();
        getMockMvc().perform(post(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ofertaMapper.toDto(oferta))))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void atualizar() throws Exception{
        Oferta oferta = ofertaBuilder.construir();

        getMockMvc().perform(put(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ofertaMapper.toDto(oferta))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deletar() throws Exception{
        Oferta oferta = ofertaBuilder.construir();

        getMockMvc().perform(delete(URL+"/"+oferta.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ofertaMapper.toDto(oferta))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void aceitar() throws Exception{
        Oferta oferta = ofertaBuilder.construir();

        getMockMvc().perform(patch(URL+"/aceitar/"+oferta.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ofertaMapper.toDto(oferta))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void recusar() throws Exception{
        Oferta oferta = ofertaBuilder.construir();

        getMockMvc().perform(patch(URL+"/recusar/"+oferta.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ofertaMapper.toDto(oferta))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void cancelar() throws Exception{
        Oferta oferta = ofertaBuilder.construir();

        getMockMvc().perform(patch(URL+"/cancelar/"+oferta.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ofertaMapper.toDto(oferta))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
