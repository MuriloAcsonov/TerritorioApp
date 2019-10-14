package com.muriloacsonov.territorio.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
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

        ftMeusMapas.setEnabled(true);
        ftEmUso.setEnabled(true);
        ftFechado.setEnabled(true);
        ftGrupo.setEnabled(true);

    }

    public void CarregarListaMapas(List<Mapa> pMapas, MapaAdapter.onMapItemListener pMapItemListener){

        MapaAdapter mapaAdapter = new MapaAdapter(pMapas, cAdm, pMapItemListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(cActivity.getApplicationContext());
        listaMapas.setLayoutManager(mLayoutManager);
        listaMapas.setItemAnimator(new DefaultItemAnimator());
        listaMapas.setAdapter(mapaAdapter);

    }

    public List<Mapa> aplicarFiltros(Filtros pFiltro){

        Boolean stFiltroAplicado = false;

        if(pFiltro.getStMeusMapas()){

            pFiltro.setMapasFiltro(meusMapasFiltro(pFiltro.getMapas(), pFiltro.getDirigente()));
            ftMeusMapas.setBackgroundTintList(cActivity.getResources().getColorStateList(R.color.lightpurple));
            ftMeusMapas.setTextColor(cActivity.getResources().getColorStateList(R.color.white));

            ftEmUso.setEnabled(false);
            ftFechado.setEnabled(false);

            stFiltroAplicado = true;

        }
        else{
            ftMeusMapas.setBackgroundTintList(cActivity.getResources().getColorStateList(R.color.filtersmain));
            ftMeusMapas.setTextColor(cActivity.getResources().getColorStateList(R.color.black));
            ftFechado.setEnabled(true);
        }

        if(pFiltro.getGrupo() != -1){

            if(stFiltroAplicado){
                pFiltro.setMapasFiltro(grupoFiltro(pFiltro.getMapasFiltro(), pFiltro.getGrupo()));
            }
            else{
                pFiltro.setMapasFiltro(grupoFiltro(pFiltro.getMapas(), pFiltro.getGrupo()));
                ftEmUso.setEnabled(true);
                stFiltroAplicado = true;
            }

            ftFechado.setEnabled(true);
            ftMeusMapas.setEnabled(true);

            ftGrupo.setBackgroundTintList(cActivity.getResources().getColorStateList(R.color.lightpurple));
            ftGrupo.setTextColor(cActivity.getResources().getColorStateList(R.color.white));

        }
        else{
            ftGrupo.setBackgroundTintList(cActivity.getResources().getColorStateList(R.color.filtersmain));
            ftGrupo.setTextColor(cActivity.getResources().getColorStateList(R.color.black));
        }

        if(pFiltro.getStFechado()){

            List<Mapa> mMapa;

            if(stFiltroAplicado){
                mMapa = pFiltro.getMapasFiltro();
            }
            else{
                mMapa = new ArrayList<Mapa>();
                mMapa.addAll(pFiltro.getMapas());

                if(pFiltro.getOrderBy() == 2){
                    stFiltroAplicado = false;
                }
                else{
                    stFiltroAplicado = true;
                }
            }

            if(!pFiltro.getStMeusMapas()){
                ftMeusMapas.setEnabled(false);
                ftEmUso.setEnabled(false);
            }
            else{
                ftMeusMapas.setEnabled(true);
                ftEmUso.setEnabled(true);
            }

            switch (pFiltro.getOrderBy()){

                case 0:

                    Drawable filterDraw = cActivity.getResources().getDrawable(R.drawable.ic_arrow_down);
                    filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

                    ftFechado.setCompoundDrawables(null, null, filterDraw, null);
                    ftFechado.setBackgroundTintList(cActivity.getResources().getColorStateList(R.color.lightpurple));
                    ftFechado.setTextColor(cActivity.getResources().getColorStateList(R.color.white));

                    pFiltro.setMapasFiltro(ultimabaixaFiltro(mMapa, pFiltro.getOrderBy()));

                    break;

                case 1:

                    filterDraw = cActivity.getResources().getDrawable(R.drawable.ic_arrow_up);
                    filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

                    ftFechado.setCompoundDrawables(null, null, filterDraw, null);

                    pFiltro.setMapasFiltro(ultimabaixaFiltro(mMapa, pFiltro.getOrderBy()));

                    break;

            }

        }
        else{

            Drawable filterDraw = cActivity.getResources().getDrawable(R.drawable.ic_filter_du);
            filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

            ftFechado.setCompoundDrawables(null, null, filterDraw, null);
            ftFechado.setBackgroundTintList(cActivity.getResources().getColorStateList(R.color.filtersmain));
            ftFechado.setTextColor(cActivity.getResources().getColorStateList(R.color.black));

            pFiltro.setMapasFiltro(pFiltro.getMapasFiltro());

        }

        if(pFiltro.getStEmUso()){

            if(stFiltroAplicado){
                pFiltro.setMapasFiltro(emusoFiltro(pFiltro.getMapasFiltro()));
            }
            else{
                pFiltro.setMapasFiltro(emusoFiltro(pFiltro.getMapas()));
                stFiltroAplicado = true;
            }

            ftFechado.setEnabled(false);
            ftMeusMapas.setEnabled(false);

            ftEmUso.setBackgroundTintList(cActivity.getResources().getColorStateList(R.color.lightpurple));
            ftEmUso.setTextColor(cActivity.getResources().getColorStateList(R.color.white));

        }
        else{
            ftEmUso.setBackgroundTintList(cActivity.getResources().getColorStateList(R.color.filtersmain));
            ftEmUso.setTextColor(cActivity.getResources().getColorStateList(R.color.black));
        }

        if(!stFiltroAplicado){

            List<Mapa> mMapa = new ArrayList<Mapa>();
            mMapa.addAll(pFiltro.getMapas());

            pFiltro.setMapasFiltro(mMapa);

            ftMeusMapas.setEnabled(true);
            ftEmUso.setEnabled(true);
            ftFechado.setEnabled(true);
            ftGrupo.setEnabled(true);

        }

        return pFiltro.getMapasFiltro();
    }

    private List<Mapa> meusMapasFiltro(List<Mapa> pMapas, Dirigente pUsuario){

        List<Mapa> mMapas = new ArrayList<Mapa>();

        for(Mapa mapa : pMapas){

            if(mapa.getUsuarioRef() != null){

                if(mapa.getUsuario().equals(pUsuario.getEmail())){
                    mMapas.add(mapa);
                }
            }

        }

        return mMapas;

    }

    private List<Mapa> ultimabaixaFiltro(List<Mapa> pMapas, int pOrderBy){

        List<Mapa> mMapas = new ArrayList<Mapa>();

        for(Mapa mMapa : pMapas){

            if(mMapa.getUsuarioRef() == null){

                mMapas.add(mMapa);

            }

        }

        switch (pOrderBy){

            case 0:

                Collections.sort(mMapas, new MapaSort.MapaAscSort());

                break;

            case 1:

                Collections.sort(mMapas, new MapaSort.MapaDescSort());

                break;

        }

        return mMapas;

    }

    private List<Mapa> grupoFiltro(List<Mapa> pMapas, int pGrupo){

        List<Mapa> mMapas = new ArrayList<Mapa>();

        if(pGrupo >= 0) {

            for (Mapa mMapa : pMapas) {

                if (mMapa.getGrupo() == pGrupo) {
                    mMapas.add(mMapa);
                }

            }

        }
        else{
            mMapas.addAll(pMapas);
        }

        return mMapas;

    }

    private List<Mapa> emusoFiltro(List<Mapa> pMapas){

        List<Mapa> mMapas = new ArrayList<Mapa>();

        for(Mapa mMapa : pMapas){

            if(mMapa.getUsuarioRef() != null){
                mMapas.add(mMapa);
            }

        }

        return mMapas;

    }

}
