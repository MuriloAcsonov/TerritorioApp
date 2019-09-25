package com.muriloacsonov.territorio.model;

import java.io.Serializable;

public class Dirigente implements Serializable {

    private String nome;
    private String congregacao;
    private String email;
    private String senha;
    private String grupo;
    private Boolean adm;

    //Getters and Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(String congregacao) {
        this.congregacao = congregacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Boolean getAdm() {
        return adm;
    }

    public void setAdm(Boolean adm) {
        this.adm = adm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
