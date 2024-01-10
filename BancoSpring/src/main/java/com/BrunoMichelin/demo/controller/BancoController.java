package com.BrunoMichelin.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BrunoMichelin.demo.dto.ContaDTO;
import com.BrunoMichelin.demo.dto.ContaRequestDTO;
import com.BrunoMichelin.demo.dto.DepositoRequestDTO;
import com.BrunoMichelin.demo.dto.TransferenciaDTO;
import com.BrunoMichelin.demo.dto.TransferenciaRequestDTO;
import com.BrunoMichelin.demo.service.BancoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/banco")
public class BancoController {
    
    @Autowired
    private BancoService service;

    @PostMapping("/transferir")
    @CircuitBreaker(name = "transferirError", fallbackMethod = "erroTransferencia")
    public ResponseEntity<TransferenciaDTO> transferirSaldo(@Valid @RequestBody TransferenciaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.transferencia(request));
    }
    
    public ResponseEntity<String> erroTransferencia(TransferenciaRequestDTO request, Exception e) {
        return ResponseEntity.status(HttpStatus.OK).body("Erro ao transferir saldo - Descrição: " + e.getMessage());
    }

    @PostMapping("/depositar")
    @CircuitBreaker(name = "depositoError", fallbackMethod = "erroDepositar")
    public ResponseEntity<ContaDTO> depositar(@Valid @RequestBody DepositoRequestDTO request) {
         return ResponseEntity.status(HttpStatus.OK).body(service.deposito(request));
    }
     
    public ResponseEntity<String> erroDepositar(DepositoRequestDTO request, Exception e) {
        return ResponseEntity.status(HttpStatus.OK).body("Erro ao depositar saldo - Descrição: " + e.getMessage());
    }
}
