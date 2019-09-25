package com.muriloacsonov.territorio.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.muriloacsonov.territorio.R;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Mapa;

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
        holder.nomeGrupo.setText(mMapa.getNomeGrupo());

        if (mMapa.getUsuario().isEmpty() || !cAdm) {
            holder.infoMapa.setText(mMapa.getUltimabaixa().toDate().toString());
        } else if(!mMapa.getUsuario().isEmpty() && cAdm) {

            //String mNome = "";

            //for(Dirigente mDirigente : cDirigentes){
            //    if(mMapa.getUsuario() == mDirigente.getEmail()) mNome = mDirigente.getNome();
            //}

            if(mMapa.getUsuario().isEmpty()){
                holder.infoMapa.setText(mMapa.getUltimabaixa().toDate().toString());
            }
            else{
                holder.infoMapa.setText(mMapa.getUsuario());
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
