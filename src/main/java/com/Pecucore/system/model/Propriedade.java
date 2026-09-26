package com.Pecucore.system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "propriedade")

public class Propriedade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String localizacao;

    public String getNome(){return nome;}
    public String getLocalizacao(){return localizacao;}
    public Long getId(){return id;}

    public void setNome(String nome){this.nome = nome;}
    public void setLocalizacao(String localizacao){this.localizacao = localizacao;}

}
