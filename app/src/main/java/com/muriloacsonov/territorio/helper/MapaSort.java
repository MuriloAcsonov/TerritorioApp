package com.muriloacsonov.territorio.helper;

import com.muriloacsonov.territorio.model.Mapa;

import java.util.Comparator;

public class MapaSort {

    public static class MapaAscSort implements Comparator<Mapa> {

        @Override
        public int compare(Mapa m1, Mapa m2) {
            return m1.getUltimabaixa().compareTo(m2.getUltimabaixa());
        }

    }

    public static class MapaDescSort implements Comparator<Mapa>{

        @Override
        public int compare(Mapa m1, Mapa m2) {
            return m2.getUltimabaixa().compareTo(m1.getUltimabaixa());
        }

    }

}
