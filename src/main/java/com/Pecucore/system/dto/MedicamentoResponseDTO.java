package com.Pecucore.system.dto;

import com.Pecucore.system.model.Medicamento;

import java.time.LocalDate;

public record MedicamentoResponseDTO(
        Long id,
        int brinco,
        String nome,
        String motivo,
        double dosagem,
        int carenciaDias,
        LocalDate data,
        LocalDate dataFimCarencia,
        Double valor
) {

    public MedicamentoResponseDTO(Medicamento medicamento) {
        this(
                medicamento.getId(),
                medicamento.getAnimal().getBrinco(),
                medicamento.getNome(),
                medicamento.getMotivo(),
                medicamento.getDosagem(),
                medicamento.getCarenciaDias(),
                medicamento.getData(),
                medicamento.getDataFimCarencia(),
                medicamento.getValor()
        );
    }
}