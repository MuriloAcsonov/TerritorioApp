package com.muriloacsonov.territorio.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class Mapa implements Serializable {

    private String id;
    private int grupo;
    private String imagem;
    private Timestamp ultimabaixa;
    private String usuario;

    //GETTERS && SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Timestamp getUltimabaixa() {
        return ultimabaixa;
    }

    public void setUltimabaixa(Timestamp ultimabaixa) {
        this.ultimabaixa = ultimabaixa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
