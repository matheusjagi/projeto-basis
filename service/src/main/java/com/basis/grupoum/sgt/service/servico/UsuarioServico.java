package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.repositorio.UsuarioRepositorio;
import com.basis.grupoum.sgt.service.servico.dto.EmailDTO;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioDTO;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioListagemDTO;
import com.basis.grupoum.sgt.service.servico.exception.RegraNegocioException;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioListagemMapper;
import com.basis.grupoum.sgt.service.servico.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServico {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioListagemMapper usuarioListagemMapper;
    private final EmailServico emailServico;

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

    public UsuarioDTO obterPorId(Long id){
        Usuario usuario = getUsuario(id);
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setToken(criptografiaSHA2(usuario.getCpf()));

        //validar cpf
        //validar email

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

    public String criptografiaSHA2(String cpf) {
        String msgDecode = null;

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(cpf.getBytes("UTF-8"));
            msgDecode  = new String(messageDigest, "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return msgDecode;
    }
}
