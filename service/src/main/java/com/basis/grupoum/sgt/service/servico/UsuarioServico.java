package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.repositorio.UsuarioRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.EmailDTO;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioDTO;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioListagemDTO;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioLoginDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioListagemMapper;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioLoginMapper;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioMapper;
import com.basis.grupoum.sgt.service.servico.util.CriptografiaSHA2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServico {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioListagemMapper usuarioListagemMapper;
    private final UsuarioLoginMapper usuarioLoginMapper;
    private final EmailServico emailServico;

    public UsuarioLoginDTO autenticacao(UsuarioLoginDTO usuarioLoginDTO){
        UsuarioLoginDTO usuLoginDTO = usuarioLoginMapper.toDto(usuarioRepositorio.findByEmailAndToken(usuarioLoginDTO.getEmail(), usuarioLoginDTO.getToken()));
        return usuLoginDTO;
    }

    public List<UsuarioListagemDTO> listar (){
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarioListagemMapper.toDto(usuarios);
    }

    public Usuario getUsuario(Long id){
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Usuario não encontrado"));
        return usuario;
    }

    public List<UsuarioListagemDTO> getUsuarioByNome(String nome){
        return usuarioListagemMapper.toDto(usuarioRepositorio.findByNomeContaining(nome));
    }

    public UsuarioDTO getById(Long id){
        Usuario usuario = getUsuario(id);
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO getByToken(String token){
        Usuario usuario = usuarioRepositorio.findByToken(token);
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        validarCPF(usuarioDTO);
        usuario.setToken(CriptografiaSHA2.geraCriptografia(usuario.getCpf()));
        usuarioRepositorio.save(usuario);
        emailServico.sendEmail(criarEmailUsuario(usuario));
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

    private EmailDTO criarEmailUsuario(Usuario usuario){
        EmailDTO email = new EmailDTO();

        email.setAssunto("Cadastro de Usuario");
        email.setCorpo("Você se cadastrou no SGTPM! Seu TOKEN de acesso é: "+usuario.getToken());
        email.setDestinatario(usuario.getEmail());

        return email;
    }

    private void validarCPF(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepositorio.findByCpf(usuarioDTO.getCpf());

        if(usuario != null && !usuario.getId().equals(usuarioDTO.getId())){
            throw new RegraNegocioException("CPF já cadastrado.");
        }
    }

}
