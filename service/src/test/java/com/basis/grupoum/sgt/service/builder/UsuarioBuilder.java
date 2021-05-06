package com.basis.grupoum.sgt.service.builder;

import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.servico.UsuarioServico;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioDTO;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioBuilder extends ConstrutorEntidade<Usuario> {

    @Autowired
    private UsuarioServico usuarioServico;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public Usuario construirEntidade() {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario Teste");
        usuario.setCpf("10339035072");
        usuario.setEmail("teste@gmail.com");
        usuario.setDataNascimento(LocalDate.now());
        return usuario;
    }

    @Override
    public Usuario persistir(Usuario entidade) {
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(entidade);
        return usuarioMapper.toEntity(usuarioServico.salvar(usuarioDTO));
    }
}
