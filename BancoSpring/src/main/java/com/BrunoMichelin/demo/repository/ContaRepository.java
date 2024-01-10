package com.BrunoMichelin.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BrunoMichelin.demo.model.Conta;
import com.BrunoMichelin.demo.model.ContaId;
import com.BrunoMichelin.demo.model.Pessoa;

public interface ContaRepository  extends JpaRepository<Conta, ContaId> {
    
    //@Query(value = "SELECT c from Conta c where c.id = :id and c.agencia = :agencia")
    //Conta findByIdAgenciaId(long agencia, Long id);

    List<Conta> findByPessoa(Pessoa pessoa);
}
