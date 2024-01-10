package com.BrunoMichelin.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@Table(name = "conta", uniqueConstraints=@UniqueConstraint(columnNames={"id", "agencia"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conta {

    @EmbeddedId
    private ContaId contaId;

    @NotNull
    @Positive
    private BigDecimal saldo = BigDecimal.valueOf(0, 2);
    
    @NotNull @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(optional = true)
    private Pessoa pessoa;
}
