package com.Pecucore.system.model;

import jakarta.persistence.*;

import java.io.Serial;

@Entity
@Table(name="pesagem")
public class Pesagem extends RegistroAnimal {

    @Serial
    private static final long serialVersionUID = 1L;

    private double peso;

    public double getPeso() {return peso;}

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
