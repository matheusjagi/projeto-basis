package com.basis.grupoum.sgt.service.repositorio;

import com.basis.grupoum.sgt.service.dominio.Usuario;
import com.basis.grupoum.sgt.service.servico.dto.UsuarioLoginDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNomeContaining(String nome);

    Usuario findByCpf(String cpf);

    Usuario findByToken(String token);

    Usuario findByEmailAndToken(String email, String token);

}
