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
import com.muriloacsonov.territorio.model.Mapa;

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
            ftGrupo.setVisibility(View.VISIBLE);
        }
        else{
            ftEmUso.setVisibility(View.GONE);
            ftGrupo.setVisibility(View.GONE);
        }

    }

    public void CarregarListaMapas(List<Mapa> pMapas, List<Dirigente> pDirigentes){

        MapaAdapter mapaAdapter = new MapaAdapter(pMapas, pDirigentes, cAdm);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(cActivity.getApplicationContext());
        listaMapas.setLayoutManager(mLayoutManager);
        listaMapas.setItemAnimator(new DefaultItemAnimator());
        listaMapas.setAdapter(mapaAdapter);

    }


}
