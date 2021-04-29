package com.basis.grupoum.sgt.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ITEM")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM")
    @SequenceGenerator(name = "SEQ_ITEM", allocationSize = 1, sequenceName = "SEQ_ITEM")
    @Column(name="ID")
    private Long id;

    @Column(name="NOME")
    private String nome;

    @Column(name="DESCRICAO")
    private String descricao;

    @Column(name="FOTO")
    private String foto;

    @Column(name="DISPONIBILIDADE")
    private boolean disponibilidade;

    @OneToMany
    private int id_usuario;

    @OneToMany
    private int id_categoria;

}
