package com.basis.grupoum.sgt.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "CATEGORIA")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATEGORIA")
    @SequenceGenerator(name = "SEQ_CATEGORIA", allocationSize = 1, sequenceName = "SEQ_CATEGORIA")
    @Column(name="ID")
    private Long id;

    @Column(name="DESCRICAO")
    private String descricao;

}
