package com.basis.grupoum.sgt.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

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

    @OneToMany
    private int id_item;

    @OneToMany
    private int id_usuario_of;

    @OneToMany
    private int id_situacao;

}
