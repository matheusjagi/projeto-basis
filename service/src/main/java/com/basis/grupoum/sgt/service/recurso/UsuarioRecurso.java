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
@RequestMapping("api/usuario")
@RequiredArgsConstructor
public class UsuarioRecurso {

    private final UsuarioServico usuarioServico;

    @GetMapping
    public ResponseEntity<List<UsuarioListagemDTO>> listar(){
        List<Usuario> usuarios = usuarioServico.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterPorId(@PathVariable("id") Long idUsuario) {
        UsuarioDTO usuario = usuarioServico.obterPorId(idUsuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvar (@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuario = usuarioServico.salvar(usuarioDTO);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizar (@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuario = usuarioServico.atualizar(usuarioDTO);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar (@PathVariable("id") Long idUsuario){
        usuarioServico.deletar(idUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
