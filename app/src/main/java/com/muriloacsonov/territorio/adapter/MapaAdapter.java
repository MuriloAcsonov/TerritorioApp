package com.muriloacsonov.territorio.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.muriloacsonov.territorio.R;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Mapa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MapaAdapter extends RecyclerView.Adapter<MapaAdapter.CustomViewHolder> {

    private List<Mapa> cMapas;
    private Boolean cAdm;
    List<Dirigente> cDirigentes;

    public MapaAdapter(List<Mapa> pMapas, List<Dirigente> pDirigentes, Boolean pAdm) {
        this.cMapas = pMapas;
        cAdm = pAdm;
       // cDirigentes = pDirigentes;
    }

    public Object getItem(int i) {
        return cMapas.get(i);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_mapa_lista, null);

        CustomViewHolder customViewHolder = new CustomViewHolder(mView);

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        Mapa mMapa = cMapas.get(position);

        holder.numeroMapa.setText("Mapa " + mMapa.getId());
        holder.nomeGrupo.setText(mMapa.getNmGrupo());

        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (mMapa.getUsuarioRef() == null || !cAdm) {
            holder.infoMapa.setText(mDateFormat.format(mMapa.getUltimabaixa().toDate()));
        } else if(cAdm) {

            if(mMapa.getUsuarioRef() == null){
                holder.infoMapa.setText(mDateFormat.format(mMapa.getUltimabaixa().toDate()));
            }
            else{
                holder.infoMapa.setText(mMapa.getUsuarioRef().getNome());
            }

        }

    }

    @Override
    public long getItemId(int i) {
        return Long.valueOf(cMapas.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return cMapas.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        protected TextView numeroMapa;
        protected TextView nomeGrupo;
        protected TextView infoMapa;

        public CustomViewHolder(View pView){
            super(pView);

            this.numeroMapa = (TextView) pView.findViewById(R.id.item_numero_mapa);
            this.nomeGrupo = (TextView) pView.findViewById(R.id.item_nome_grupo);
            this.infoMapa = (TextView) pView.findViewById(R.id.item_info_mapa);

        }

    }

}
