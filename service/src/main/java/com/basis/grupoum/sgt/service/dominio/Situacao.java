/*package com.basis.grupoum.sgt.service.dominio;

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
@Table(name = "SITUACAO")
public class Situacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SITUACAO")
    @SequenceGenerator(name = "SEQ_SITUACAO", allocationSize = 1, sequenceName = "SEQ_SITUACAO")
    @Column(name="ID")
    private Long id;

    @Column(name="DESCRICAO")
    private String descricao;

}*/
