package com.muriloacsonov.territorio.model;

import java.io.Serializable;
import java.util.List;

public class Congregacao implements Serializable {

    private String id;
    private String[] grupos;
    private String nome;
    private List<Mapa> mapas;

    // GETTERS && SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getGrupos() {
        return grupos;
    }

    public void setGrupos(String[] grupos) {
        this.grupos = grupos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Mapa> getMapas() {
        return mapas;
    }

    public void setMapas(List<Mapa> mapas) {
        this.mapas = mapas;
    }
}
