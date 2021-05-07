package com.basis.grupoum.sgt.service.servico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty
    private String nome;

    @NotNull
    @Email
    private String email;

    @NotNull
    @CPF
    private String cpf;

    @NotNull
    @Past
    private LocalDate dataNascimento;

}
