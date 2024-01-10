package com.BrunoMichelin.demo.dto;

import java.math.BigDecimal;

import com.BrunoMichelin.demo.model.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO {
    
    private ContaIdDTO id;
    
    private BigDecimal saldo = BigDecimal.valueOf(0, 2);
    
    private Status status;

}
