package com.Pecucore.system.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CalculadoraCarencia {
    public LocalDate calculateDataFimCarencia(LocalDate dataAplicacao, int carenciaDias) {
        return dataAplicacao.plusDays(carenciaDias);
    }
}
