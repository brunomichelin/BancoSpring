package com.BrunoMichelin.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.BrunoMichelin.demo.dto.NotificacaoFeignDTO;

@FeignClient(value = "APINotificacao", url = "${feign.apis.notificacao.url}")//, configuration = OAuthFeignConfig.class)
public interface NotificacaoFeignClient {
    
    @GetMapping()
    NotificacaoFeignDTO notificar();
}
