package com.BrunoMichelin.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.BrunoMichelin.demo.model.Pessoa;

public interface PessoaRepository  extends JpaRepository<Pessoa, String>{

    
}
