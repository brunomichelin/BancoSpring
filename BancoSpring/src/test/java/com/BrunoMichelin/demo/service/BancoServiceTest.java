package com.BrunoMichelin.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.boot.test.context.SpringBootTest;

import com.BrunoMichelin.demo.dto.ContaDTO;
import com.BrunoMichelin.demo.dto.ContaIdDTO;
import com.BrunoMichelin.demo.dto.DepositoRequestDTO;
import com.BrunoMichelin.demo.dto.NotificacaoFeignDTO;
import com.BrunoMichelin.demo.dto.TransferenciaRequestDTO;
import com.BrunoMichelin.demo.feign.NotificacaoFeignClient;
import com.BrunoMichelin.demo.model.Conta;
import com.BrunoMichelin.demo.model.ContaId;
import com.BrunoMichelin.demo.model.Pessoa;
import com.BrunoMichelin.demo.model.Status;
import com.BrunoMichelin.demo.repository.ContaRepository;

@SpringBootTest
public class BancoServiceTest {
    
    @Mock
    private NotificacaoFeignClient notificacaoFeignClient;
    
	@Mock
	private ContaRepository contaRepository;

	@Mock
	private ModelMapper mapper;

    @InjectMocks
    private BancoService bancoService;

    TransferenciaRequestDTO transferenciaRequest = new TransferenciaRequestDTO();
    
    DepositoRequestDTO depositoRequest = new DepositoRequestDTO();

    ContaIdDTO remetenteContaIdDTO = new ContaIdDTO(1L, 1L);
    ContaIdDTO destinatarioContaIdDTO = new ContaIdDTO(2L, 2L);
    
    ContaId remetenteContaId = new ContaId(1L, 1L);
    ContaId destinatarioContaId = new ContaId(2L, 2L);

    ContaDTO remetenteDTO = new ContaDTO(remetenteContaIdDTO, new BigDecimal(1000), Status.ATIVO);
    ContaDTO destinatarioDTO = new ContaDTO(destinatarioContaIdDTO, new BigDecimal(1000), Status.ATIVO);

    Pessoa bruno = new Pessoa();

    Conta remetente = new Conta(remetenteContaId, new BigDecimal(1000), Status.ATIVO, bruno);
    Conta destinatario = new Conta(destinatarioContaId, new BigDecimal(1000), Status.ATIVO, bruno);

    @Before(value = "init")
    public void init() {

    }

    @Test
    public void transferenciaTest() {

        MockitoAnnotations.initMocks(this);
        
        transferenciaRequest.setDestinatario(destinatarioContaIdDTO);
        transferenciaRequest.setRemetente(remetenteContaIdDTO);
        transferenciaRequest.setValor(new BigDecimal(100));

        Mockito.when(mapper.map(remetenteContaIdDTO, ContaId.class)).thenReturn(remetenteContaId);
        Mockito.when(mapper.map(destinatarioContaIdDTO, ContaId.class)).thenReturn(destinatarioContaId);

        when(notificacaoFeignClient.notificar()).thenReturn(new NotificacaoFeignDTO(true));
        when(contaRepository.findById(any())).thenReturn(Optional.of(remetente));
        //when(contaRepository.findById(destinatarioContaId)).thenReturn(Optional.of(destinatario));

        Mockito.when(mapper.map(remetente, ContaDTO.class)).thenReturn(remetenteDTO);
        Mockito.when(mapper.map(destinatario, ContaDTO.class)).thenReturn(destinatarioDTO);

        when(contaRepository.save(any())).thenReturn(remetente);

        Assert.notNull(bancoService.transferencia(transferenciaRequest));
    }

    @Test
    public void depositoTest() {

        MockitoAnnotations.initMocks(this);

        depositoRequest.setConta(remetenteContaIdDTO);
        depositoRequest.setValor(new BigDecimal(1000));

        Mockito.when(mapper.map(remetenteContaIdDTO, ContaId.class)).thenReturn(remetenteContaId);
        Mockito.when(contaRepository.findById(any())).thenReturn(Optional.of(remetente));
        Mockito.when(notificacaoFeignClient.notificar()).thenReturn(new NotificacaoFeignDTO(true));
        when(contaRepository.save(any())).thenReturn(remetente);

        Assert.notNull(bancoService.deposito(depositoRequest));
    }
}
