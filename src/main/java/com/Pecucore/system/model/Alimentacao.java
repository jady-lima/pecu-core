package com.Pecucore.system.model;

import jakarta.persistence.*;

@Entity
@Table(name="alimentacao")
public class Alimentacao extends RegistroAnimal {

    @Enumerated(EnumType.STRING)
    private TipoAlimentacao tipo;

    private double quantidade;

    public TipoAlimentacao getTipo() {
        return tipo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setTipo(TipoAlimentacao tipo) {
        this.tipo = tipo;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
