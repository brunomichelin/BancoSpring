package com.BrunoMichelin.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BrunoMichelin.demo.service.BancoService;

@RestController
@RequestMapping("/banco")
public class BancoController {
    
    @Autowired
    private BancoService service;

    
}
