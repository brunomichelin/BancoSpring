package com.BrunoMichelin.demo.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BrunoMichelin.demo.dto.ContaDTO;
import com.BrunoMichelin.demo.dto.ContaIdDTO;
import com.BrunoMichelin.demo.dto.DepositoRequestDTO;
import com.BrunoMichelin.demo.dto.TransferenciaDTO;
import com.BrunoMichelin.demo.dto.TransferenciaRequestDTO;
import com.BrunoMichelin.demo.feign.NotificacaoFeignClient;
import com.BrunoMichelin.demo.model.Conta;
import com.BrunoMichelin.demo.model.ContaId;
import com.BrunoMichelin.demo.model.Pessoa;
import com.BrunoMichelin.demo.repository.ContaRepository;
import com.BrunoMichelin.demo.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BancoService {
    
    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private NotificacaoFeignClient notificacaoClient;

    @Transactional
    public TransferenciaDTO transferencia(TransferenciaRequestDTO request) {

        Optional<Conta> remetenteOp = contaRepository.findById(modelMapper.map(request.getRemetente(), ContaId.class));
        Optional<Conta> destinatarioOp = contaRepository.findById(modelMapper.map(request.getDestinatario(), ContaId.class));

        Conta remetente = null;
        Conta destinatario = null;

        if (!remetenteOp.isPresent())
            throw new EntityNotFoundException("Remetente não encontrado!");
        else
            remetente = remetenteOp.get();

        if (!destinatarioOp.isPresent())
            throw new EntityNotFoundException("Destinatario não encontrado!");
        else
            destinatario = destinatarioOp.get();
        
        if (remetente.getSaldo().compareTo(request.getValor()) == 1) {
            remetente.setSaldo(remetente.getSaldo().subtract(request.getValor()));
            destinatario.setSaldo(destinatario.getSaldo().add(request.getValor()));

            contaRepository.save(remetente);
            contaRepository.save(destinatario);
        }
        else
            throw new RuntimeException("Saldo Insuficiente! - Não pode zerar o Saldo.");

        TransferenciaDTO transf = new TransferenciaDTO();
        //transf.setRemetente(modelMapper.map(remetente, ContaDTO.class));
        //transf.setDestinatario(modelMapper.map(destinatario, ContaDTO.class));
        transf.setRemetente(new ContaDTO(new ContaIdDTO(remetente.getContaId().getId(), remetente.getContaId().getAgencia()), remetente.getSaldo(), remetente.getStatus()));
        transf.setDestinatario(new ContaDTO(new ContaIdDTO(destinatario.getContaId().getId(), destinatario.getContaId().getAgencia()), destinatario.getSaldo(), destinatario.getStatus()));
        transf.setValor(request.getValor());

        enviarNotificacao(remetente.getPessoa());
        enviarNotificacao(destinatario.getPessoa());

        return transf;
    }

    @Transactional
    public ContaDTO deposito(DepositoRequestDTO request) {
        
        Optional<Conta> contaOp = contaRepository.findById(modelMapper.map(request.getConta(), ContaId.class));

        Conta conta = null;

        if (!contaOp.isPresent())
            throw new EntityNotFoundException("Conta para deposito não encontrado!");
        else
            conta = contaOp.get();

        if (request.getValor().compareTo(new BigDecimal(0)) == 1) {
            conta.setSaldo(conta.getSaldo().add(request.getValor()));

            contaRepository.save(conta);
        }
        else
            throw new RuntimeException("Deposito vazio!");

        enviarNotificacao(conta.getPessoa());

        //return modelMapper.map(conta, ContaDTO.class);

        return new ContaDTO(new ContaIdDTO(conta.getContaId().getId(), conta.getContaId().getAgencia()), conta.getSaldo(), conta.getStatus());
    }

    public void enviarNotificacao(Pessoa pessoa) {

        // Notificar Pessoa
        if (notificacaoClient.notificar().isMessageSent()) {
            System.out.println("Notificação enviada a " + pessoa.getNome());
        }
        else {
            throw new RuntimeException("Notificação não enviada para o " + pessoa.getNome());
        }
    }


}
