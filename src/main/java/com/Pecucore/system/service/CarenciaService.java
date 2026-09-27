package com.Pecucore.system.service;

import com.Pecucore.system.repository.VacinacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CarenciaService {

    @Autowired
    private VacinacaoRepository vacinacaoRepository;

    public boolean estaEmCarencia(Long animalId) {
        return vacinacaoRepository.existsByAnimalIdAndDataFimCarenciaAfter(animalId, LocalDate.now());
    }
}
