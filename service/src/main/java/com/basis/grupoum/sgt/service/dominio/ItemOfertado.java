package com.basis.grupoum.sgt.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ITEM_OFERTADO")
public class ItemOfertado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM_OFERTADO")
    @SequenceGenerator(name = "SEQ_ITEM_OFERTADO", allocationSize = 1, sequenceName = "SEQ_ITEM_OFERTADO")
    @Column(name="ID")
    private Long id;

    @OneToMany
    private int id_item;

    @OneToMany
    private int id_oferta;

}