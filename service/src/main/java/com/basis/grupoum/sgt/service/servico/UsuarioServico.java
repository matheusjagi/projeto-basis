package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.repositorio.UsuarioRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioDTO;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioListagemDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioListagemMapper;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServico {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioListagemMapper usuarioListagemMapper;


    public List<UsuarioListagemDTO> listar (){
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarioListagemMapper.toDto(usuarios);
    }

    private Usuario getUsuario(Long id){
        Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("Usuario nao encontrado"));
        return usuario;
    }

    public UsuarioDTO obterPorId(Long id){
        Usuario usuario = getUsuario(id);
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setToken(UUID.randomUUID().toString());
        usuarioRepositorio.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO atualizar(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        Usuario usuarioSalvo = getUsuario(usuario.getId());
        usuario.setToken(usuarioSalvo.getToken());
        usuarioRepositorio.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    public void deletar(Long idUsuario){
        usuarioRepositorio.deleteById(idUsuario);
    }
}
