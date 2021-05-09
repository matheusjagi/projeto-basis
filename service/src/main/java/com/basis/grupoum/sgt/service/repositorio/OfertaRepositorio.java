package com.basis.grupoum.sgt.service.repositorio;

import com.basis.grupoum.sgt.service.dominio.Item;
import com.basis.grupoum.sgt.service.dominio.Oferta;
import com.basis.grupoum.sgt.service.dominio.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfertaRepositorio extends JpaRepository<Oferta, Long> {

    List<Oferta> findAllBySituacaoId(Long id);

    List<Oferta> findAllByItemId(Long id);

}
