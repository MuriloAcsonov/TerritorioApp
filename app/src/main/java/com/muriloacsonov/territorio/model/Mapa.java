package com.muriloacsonov.territorio.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.List;

public class Mapa implements Serializable {

    private String id;
    private int grupo;
    private String imagem;
    private Timestamp ultimabaixa;
    private String nmGrupo;
    @Exclude
    private Dirigente usuarioRef;
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

    @Exclude
    public Dirigente getUsuarioRef() {
        return usuarioRef;
    }

    @Exclude
    public void setUsuarioRef(Dirigente usuarioRef) {
        this.usuarioRef = usuarioRef;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNmGrupo() {
        return nmGrupo;
    }

    public void setNmGrupo(String nmGrupo) {
        this.nmGrupo = nmGrupo;
    }
}
