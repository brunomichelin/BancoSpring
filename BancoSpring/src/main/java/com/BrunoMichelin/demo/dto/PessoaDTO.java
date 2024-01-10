package com.BrunoMichelin.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {

    private String cpfCnpj;

    private String nome;
    
    private String endereco;
    
    private String senha;

    private List<ContaDTO> contas = new ArrayList<>();  
}
