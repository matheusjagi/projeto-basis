package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Categoria;
import com.basis.grupoum.sgt.service.repositorio.CategoriaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaServico {
    private final CategoriaRepositorio categoriaRepositorio;

    public List<Categoria> buscarTodasCategorias(){
        List<Categoria> categorias = categoriaRepositorio.findAll();
        return categorias;
    }

}
