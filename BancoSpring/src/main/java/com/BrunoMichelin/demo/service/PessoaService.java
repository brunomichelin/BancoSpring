package com.BrunoMichelin.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BrunoMichelin.demo.dto.ContaDTO;
import com.BrunoMichelin.demo.dto.ContaRequestDTO;
import com.BrunoMichelin.demo.dto.PessoaDTO;
import com.BrunoMichelin.demo.dto.PessoaRequestDTO;
import com.BrunoMichelin.demo.model.Conta;
import com.BrunoMichelin.demo.model.ContaId;
import com.BrunoMichelin.demo.model.Pessoa;
import com.BrunoMichelin.demo.model.Status;
import com.BrunoMichelin.demo.repository.ContaRepository;
import com.BrunoMichelin.demo.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaService {
    
    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public List<PessoaDTO> obterTodasPessoas() {
        List<PessoaDTO> listaPessoas = pessoaRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PessoaDTO.class))
                .collect(Collectors.toList());

        return listaPessoas;
    }

    public PessoaDTO criarPessoaConta(PessoaRequestDTO request) {
        Pessoa pessoa = modelMapper.map(request, Pessoa.class);

        pessoa.setCpfCnpj(pessoa.getCpfCnpj().replace(".", "").replace("-", ""));

        Conta conta = Conta
            .builder()
            .contaId(new ContaId(request.getId(), request.getAgencia()))
            .saldo(new BigDecimal(1))
            .status(Status.ATIVO)
            .build();
        
        contaRepository.save(conta);
        
        List<Conta> contas = new ArrayList<>();
        contas.add(conta);

        pessoa.setContas(contas);

        pessoaRepository.save(pessoa);

        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    public ContaDTO criarConta(ContaRequestDTO request) {
        
        request.setCpfCnpj(request.getCpfCnpj().replace(".", "").replace("-", ""));

        Pessoa pessoa = null;

        if (!pessoaRepository.findById(request.getCpfCnpj()).isPresent())
            throw new EntityNotFoundException();
        else
            pessoa = pessoaRepository.findById(request.getCpfCnpj()).get();

        Conta conta = Conta
            .builder()
            .contaId(new ContaId(request.getId(), request.getAgencia()))
            .pessoa(pessoa)
            .saldo(request.getSaldo())
            .status(Status.ATIVO)
            .build();

        contaRepository.save(conta);

        return modelMapper.map(conta, ContaDTO.class);
    }

    public List<ContaDTO> obterTodasContas() {
        List<ContaDTO> listaContas = contaRepository.findAll().stream()
                .map(c -> modelMapper.map(c, ContaDTO.class))
                .collect(Collectors.toList());

        return listaContas;
    }
}
