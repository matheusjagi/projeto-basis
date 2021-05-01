package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.repositorio.UsuarioRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServico {

    private Usuario getUsuario(Long id){
        return UsuarioRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("Usuario nao encontrado"));
    }

    public UsuarioDTO salvar(UsuarioDTO dto){
        Usuario usuario = UsuarioMapper.toEntity(dto);
        Usuario.setToken(UUID.randomUUID().toString());
        UsuarioRepositorio.save(usuario);
        return UsuarioMapper.toDto(usuario);
    }

    public UsuarioDTO atualizar(UsuarioDTO dto){
        Usuario usuario = UsuarioMapper.toEntity(dto);
        Usuario usuarioSalvo = getUsuario(usuario.getId());
        usuario.setToken(usuarioSalvo.getToken());
        UsuarioRepositorio.save(usuario);
        return UsuarioMapper.toDto(usuario);
    }


}
