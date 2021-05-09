package com.basis.grupoum.sgt.service.repositorio;

import com.basis.grupoum.sgt.service.dominio.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepositorio extends JpaRepository<Item, Long> {

    List<Item> findAllByDisponibilidade(boolean disponibilidade);

    List<Item> findByNomeContaining(String nome);

    List<Item> findAllByCategoriaId(Long categoriaDtoId);

    List<Item> findAllByUsuarioId(Long usuarioDtoId);

}
