package com.Pecucore.system.dto;

import com.Pecucore.system.model.Alimentacao;
import com.Pecucore.system.model.TipoAlimentacao;

import java.time.LocalDate;

public record AlimentacaoResponseDTO(
        Long id,
        int brinco,
        TipoAlimentacao tipo,
        double quantidade,
        LocalDate data,
        Double valor
) {

    public AlimentacaoResponseDTO(Alimentacao alimentacao) {
        this(
            alimentacao.getId(),
            alimentacao.getAnimal().getBrinco(),
            alimentacao.getTipo(),
            alimentacao.getQuantidade(),
            alimentacao.getData(),
            alimentacao.getValor()
        );
    }
}
