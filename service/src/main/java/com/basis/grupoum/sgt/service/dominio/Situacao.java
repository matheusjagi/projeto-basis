package com.basis.grupoum.sgt.service.dominio;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "SITUACAO")
public class Situacao implements Serializable {

    @Id
    @Column(name="ID")
    private Long id;

    @Column(name="DESCRICAO")
    private String descricao;

}
