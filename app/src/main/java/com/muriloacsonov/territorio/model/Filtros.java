package com.muriloacsonov.territorio.model;

import java.util.List;

public class Filtros {

    private List<Mapa> mapas;
    private List<Mapa> mapasFiltro;
    private Boolean stMeusMapas;
    private Boolean stEmUso;
    private Boolean stGrupo;
    private Boolean stFechado;
    private Dirigente dirigente;
    private int orderBy;
    private int grupo;

    public List<Mapa> getMapas() {
        return mapas;
    }

    public void setMapas(List<Mapa> mapas) {
        this.mapas = mapas;
    }

    public List<Mapa> getMapasFiltro() {
        return mapasFiltro;
    }

    public void setMapasFiltro(List<Mapa> mapasFiltro) {
        this.mapasFiltro = mapasFiltro;
    }

    public Boolean getStMeusMapas() {
        return stMeusMapas;
    }

    public void setStMeusMapas(Boolean stMeusMapas) {
        this.stMeusMapas = stMeusMapas;
    }

    public Boolean getStEmUso() {
        return stEmUso;
    }

    public void setStEmUso(Boolean stEmUso) {
        this.stEmUso = stEmUso;
    }

    public Boolean getStGrupo() {
        return stGrupo;
    }

    public void setStGrupo(Boolean stGrupo) {
        this.stGrupo = stGrupo;
    }

    public Boolean getStFechado() {
        return stFechado;
    }

    public void setStFechado(Boolean stFechado) {
        this.stFechado = stFechado;
    }

    public Dirigente getDirigente() {
        return dirigente;
    }

    public void setDirigente(Dirigente dirigente) {
        this.dirigente = dirigente;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }
}
