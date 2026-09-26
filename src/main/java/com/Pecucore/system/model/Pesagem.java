package com.Pecucore.system.model;

import jakarta.persistence.*;

@Entity
@Table(name="pesagem")
public class Pesagem extends RegistroAnimal {

    private double peso;

    public double getPeso() {return peso;}

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
