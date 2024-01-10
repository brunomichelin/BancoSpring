package com.BrunoMichelin.demo.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaRequestDTO {
    
    @NotNull
    private String cpfCnpj;

    @NotNull
    private BigDecimal saldo = BigDecimal.valueOf(0, 2);
    
    @NotNull
    private Long agencia;
    
    @NotNull
    private Long id;
}
