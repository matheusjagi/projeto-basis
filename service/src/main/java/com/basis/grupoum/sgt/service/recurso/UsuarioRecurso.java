package com.basis.grupoum.sgt.service.recurso;

import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.repositorio.UsuarioRepositorio;
import com.basis.grupoum.sgt.service.servico.UsuarioServico;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioDTO;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioListagemDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioListagemMapper;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor
public class UsuarioRecurso {

    private final UsuarioServico usuarioServico;

    @GetMapping
    public ResponseEntity<List<UsuarioListagemDTO>> listar(){
        List<Usuario> usuarios = UsuarioRepositorio.findAll();
        return UsuarioListagemMapper.toDto(usuarios);
    }

    @GetMapping("/{id}")
    public UsuarioDTO obterPorId(Long id) {
        //Usuario usuario = UsuarioRepositorio.getOne(id);
        Usuario usuario = UsuarioRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Usuario nao encontrado"))
        return UsuarioMapper.toDto(usuario);
    }

    @PostMapping
    public UsuarioDTO salvar (UsuarioDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        UsuarioRepositorio.save(usuario);
        return UsuarioMapper.toDto(usuario);
    }

    @PutMapping
    public UsuarioDTO alterar (UsuarioDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        UsuarioRepositorio.update(usuario);
        return UsuarioMapper.toDto(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar (Long id){

    }
}
