package com.basis.grupoum.sgt.service.repositorio;

import com.basis.grupoum.sgt.service.dominio.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepositorio extends JpaRepository<Item, Long> {
}
