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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public String getNome(){return nome;}
    public String getLocalizacao(){return localizacao;}
    public Long getId(){return id;}
    public Usuario getUsuario(){return usuario;}

    public void setNome(String nome){this.nome = nome;}
    public void setLocalizacao(String localizacao){this.localizacao = localizacao;}
    public void setUsuario(Usuario usuario){this.usuario = usuario;}

}
