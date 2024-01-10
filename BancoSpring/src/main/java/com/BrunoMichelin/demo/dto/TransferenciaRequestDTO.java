package com.BrunoMichelin.demo.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaRequestDTO {
    
    @NotNull
    private ContaIdDTO remetente;

    @NotNull
    private ContaIdDTO destinatario;

    @NotNull
    @Positive
    private BigDecimal valor = BigDecimal.valueOf(0, 2);
}
