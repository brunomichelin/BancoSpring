package com.BrunoMichelin.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BrunoMichelin.demo.dto.ContaDTO;
import com.BrunoMichelin.demo.dto.ContaRequestDTO;
import com.BrunoMichelin.demo.dto.PessoaDTO;
import com.BrunoMichelin.demo.dto.PessoaRequestDTO;
import com.BrunoMichelin.demo.service.PessoaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    
    @Autowired
    private PessoaService service;

    @GetMapping("/listarTodasPessoas")
    public ResponseEntity<List<PessoaDTO>> listarTodasPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodasPessoas());
    }

    @GetMapping("/listarTodasContas")
    public ResponseEntity<List<ContaDTO>> listarTodasContas() {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodasContas());
    }

    @PostMapping("/criarPessoaConta")
    public ResponseEntity<PessoaDTO> criarPessoaConta(@Valid @RequestBody PessoaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.criarPessoaConta(request));
    }

    @PostMapping("/criarConta")
    @CircuitBreaker(name = "criarContaError", fallbackMethod = "erroCriacaoConta")
    public ResponseEntity<ContaDTO> criarConta(@Valid @RequestBody ContaRequestDTO request) {
            return ResponseEntity.status(HttpStatus.OK).body(service.criarConta(request));
    }

    public ResponseEntity<String> erroCriacaoConta(ContaRequestDTO request, Exception e) {
        return ResponseEntity.status(HttpStatus.OK).body("Erro ao criar conta - Descrição: " + e.getMessage());
    }
}
