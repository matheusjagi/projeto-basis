package com.basis.grupoum.sgt.service.recurso;

import com.basis.grupoum.sgt.service.servico.UsuarioServico;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioDTO;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioListagemDTO;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioRecurso {

    private final UsuarioServico usuarioServico;

    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginDTO> autenticacao(@RequestBody UsuarioLoginDTO usuarioLogin){
        UsuarioLoginDTO usuario = usuarioServico.autenticacao(usuarioLogin);

        ResponseEntity resposta;

        if(usuario != null){
            resposta = new ResponseEntity<>(usuario, HttpStatus.OK);
        }else{
            resposta = new ResponseEntity<>(usuario, HttpStatus.UNAUTHORIZED);
        }
        return resposta;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioListagemDTO>> listar(){
        List<UsuarioListagemDTO> usuarios = usuarioServico.listar();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioListagemDTO>> listar(@PathVariable("nome") String nome){
        List<UsuarioListagemDTO> usuarios = usuarioServico.getUsuarioByNome(nome);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterPorId(@PathVariable("id") Long idUsuario) {
        UsuarioDTO usuario = usuarioServico.getById(idUsuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<UsuarioDTO> obterPorToken(@PathVariable("token") String token) {
        UsuarioDTO usuario = usuarioServico.getByToken(token);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvar (@RequestBody @Valid UsuarioDTO usuarioDTO) {
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
