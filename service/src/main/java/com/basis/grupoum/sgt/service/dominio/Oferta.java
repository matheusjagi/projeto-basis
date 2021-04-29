package com.basis.grupoum.sgt.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "OFERTA")
public class Oferta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OFERTA")
    @SequenceGenerator(name = "SEQ_OFERTA", allocationSize = 1, sequenceName = "SEQ_OFERTA")
    @Column(name="ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ITEM")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SITUACAO")
    private Situacao situacao;

    @ManyToMany
    @JoinTable(name = "ITEM_OFERTADO", joinColumns =
            {@JoinColumn(name = "ID_OFERTA")}, inverseJoinColumns =
            {@JoinColumn(name = "ID_ITEM")})
    private List<Item> itensOfertados;


}
