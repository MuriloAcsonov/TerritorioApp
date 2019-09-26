package com.muriloacsonov.territorio.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muriloacsonov.territorio.R;
import com.muriloacsonov.territorio.adapter.MapaAdapter;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Filtros;
import com.muriloacsonov.territorio.model.Mapa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainHelper {

    private RecyclerView listaMapas;
    private Button ftMeusMapas;
    private Button ftFechado;
    private Button ftGrupo;
    private Button ftEmUso;
    private Boolean cAdm;
    private Activity cActivity;

    public MainHelper(Activity pActivity, Boolean pAdm){

        listaMapas = (RecyclerView) pActivity.findViewById(R.id.rvwMapas);
        ftEmUso = (Button) pActivity.findViewById(R.id.ftEmUso);
        ftFechado = (Button) pActivity.findViewById(R.id.ftRecorrencia);
        ftGrupo = (Button) pActivity.findViewById(R.id.ftGrupo);
        ftMeusMapas = (Button) pActivity.findViewById(R.id.ftDisponivel);

        cActivity = pActivity;
        cAdm = pAdm;

        if(cAdm){
            ftEmUso.setVisibility(View.VISIBLE);
        }
        else{
            ftEmUso.setVisibility(View.GONE);
        }

    }

    public void CarregarListaMapas(List<Mapa> pMapas){

        MapaAdapter mapaAdapter = new MapaAdapter(pMapas, cAdm);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(cActivity.getApplicationContext());
        listaMapas.setLayoutManager(mLayoutManager);
        listaMapas.setItemAnimator(new DefaultItemAnimator());
        listaMapas.setAdapter(mapaAdapter);

    }

    public static List<Mapa> aplicarFiltros(Filtros pFiltro){

        if(pFiltro.getStMeusMapas()){

            pFiltro.setMapasFiltro(meusMapasFiltro(pFiltro.getMapas(), pFiltro.getDirigente()));

        }

        if(pFiltro.getStGrupo()){

            pFiltro.setMapasFiltro(grupoFiltro(pFiltro.getMapas(), pFiltro.getGrupo()));

        }

        if(pFiltro.getStEmUso()){

            pFiltro.setMapasFiltro(emusoFiltro(pFiltro.getMapas()));

        }

        return null;
    }

    public static List<Mapa> meusMapasFiltro(List<Mapa> pMapas, Dirigente pUsuario){

        List<Mapa> mMapas = pMapas;

        for(Mapa mapa : pMapas){

            if(mapa.getUsuarioRef() != null){

                if(mapa.getUsuario().equals(pUsuario.getEmail())){
                    mMapas.add(mapa);
                }
            }

        }

        return mMapas;

    }

    public static List<Mapa> ultimabaixaFiltro(List<Mapa> pMapas, int pOrderBy){

        switch (pOrderBy){

            case 0:

                Collections.sort(pMapas, new Mapa.MapaAscSort());

                break;

            case 1:

                Collections.sort(pMapas, new Mapa.MapaDescSort());

                break;

        }

        return pMapas;

    }

    public static List<Mapa> grupoFiltro(List<Mapa> pMapas, int pGrupo){

        List<Mapa> mMapas = new ArrayList<Mapa>();

        for(Mapa mMapa : pMapas){

            if(mMapa.getGrupo() == pGrupo){
                mMapas.add(mMapa);
            }

        }

        return mMapas;

    }

    public static List<Mapa> emusoFiltro(List<Mapa> pMapas){

        List<Mapa> mMapas = new ArrayList<Mapa>();

        for(Mapa mMapa : pMapas){

            if(mMapa.getUsuarioRef() != null){
                mMapas.add(mMapa);
            }

        }

        return mMapas;

    }

}
