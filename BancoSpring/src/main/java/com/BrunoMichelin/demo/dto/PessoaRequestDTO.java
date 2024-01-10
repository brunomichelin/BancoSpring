package com.BrunoMichelin.demo.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequestDTO {
    
    @NotNull
    private String cpfCnpj;

    @NotNull
    private String nome;
    
    @NotNull
    private String endereco;
    
    @NotNull
    private String senha;

    @NotNull
    private Long id;

    @NotNull
    private Long agencia;
}
