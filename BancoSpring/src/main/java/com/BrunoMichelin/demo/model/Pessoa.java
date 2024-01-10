package com.BrunoMichelin.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pessoa", uniqueConstraints=@UniqueConstraint(columnNames={"cpfCnpj"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    private String cpfCnpj;

    @NotNull
    @Size(max=100)
    private String nome;

    @NotNull
    @Size(max=300)
    private String endereco;

    @NotNull
    @Size(min = 8, max = 20)
    private String senha;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="pessoa", fetch=FetchType.LAZY)
    private List<Conta> contas = new ArrayList<>();
}
