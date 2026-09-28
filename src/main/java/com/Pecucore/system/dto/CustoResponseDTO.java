package com.Pecucore.system.dto;

import com.Pecucore.system.model.Custo;
import com.Pecucore.system.model.OrigemCusto;
import com.Pecucore.system.model.TipoCusto;

import java.time.LocalDate;

public record CustoResponseDTO(
        Long id,
        TipoCusto tipo,
        OrigemCusto origem,
        double valor,
        LocalDate data,
        Long animalId,
        Long loteId,
        Long propriedadeId
) {

    public CustoResponseDTO(Custo custo) {
        this(
                custo.getId(),
                custo.getTipo(),
                custo.getOrigem(),
                custo.getValor(),
                custo.getData(),
                custo.getAnimal() != null ? custo.getAnimal().getId() : null,
                custo.getLote() != null ? custo.getLote().getId() : null,
                custo.getPropriedade() != null ? custo.getPropriedade().getId() : null
        );
    }
}