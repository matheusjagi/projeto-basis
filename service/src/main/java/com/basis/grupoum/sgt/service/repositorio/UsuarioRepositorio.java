package com.basis.grupoum.sgt.service.repositorio;

import com.basis.grupoum.sgt.service.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNomeContaining(String nome);

    Usuario findByCpf(String cpf);

    Usuario findByToken(String token);

}
