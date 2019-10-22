package com.muriloacsonov.territorio.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.muriloacsonov.territorio.R;
import com.muriloacsonov.territorio.model.Mapa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MapaHelper {

    LinearLayout cLayoutPhoto;
    LinearLayout cLayoutInfo;
    LinearLayout cLayoutLista;
    LinearLayout cLayoutUltimaObservacao;
    ImageView cImageMapa;
    TextView cTxtGrupo;
    TextView cTxtTitulo;
    TextView cTxtInfo;
    TextView cTxtUltimaObservacao;
    ListView cListObservacoes;
    View cFragment;
    Button cBtnMais;

    public MapaHelper(View pFragment){

        cFragment = pFragment;

        switch ((int) pFragment.getTag()){

            case 1:

                this.cLayoutPhoto = (LinearLayout) pFragment.findViewById(R.id.LayoutPhoto);
                this.cLayoutInfo = (LinearLayout) pFragment.findViewById(R.id.LayoutInfo);
                this.cLayoutLista = (LinearLayout) pFragment.findViewById(R.id.LayoutLista);
                this.cLayoutUltimaObservacao = (LinearLayout) pFragment.findViewById(R.id.LayoutUltimaObservacao);

                this.cImageMapa = (ImageView) pFragment.findViewById(R.id.imgMapa);

                this.cTxtGrupo = (TextView) pFragment.findViewById(R.id.tvwGrupoMapa);
                this.cTxtTitulo = (TextView) pFragment.findViewById(R.id.tvwInfoVariavel);
                this.cTxtInfo = (TextView) pFragment.findViewById(R.id.tvwData);
                this.cTxtUltimaObservacao = (TextView) pFragment.findViewById(R.id.tvwUltimaObservacao);

                this.cBtnMais = (Button) pFragment.findViewById(R.id.btMaisObservacoes);

                this.cListObservacoes = (ListView) pFragment.findViewById(R.id.lswObservacoes);

                break;

        }

    }

    public void CarregarInformações(Mapa pMapa, String pMeuUsuario, Context pContext){

        int mHeightFragment = cFragment.getLayoutParams().height;
        int mHeightImg = cLayoutPhoto.getLayoutParams().height;

        cLayoutInfo.getLayoutParams().height = (mHeightFragment - mHeightImg - 50) * -1;

        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        cTxtGrupo.setText(pMapa.getNmGrupo());

        if(pMapa.getUsuarioRef() == null){

            cTxtTitulo.setText("Última Baixa:");
            cTxtInfo.setText(mDateFormat.format(pMapa.getUltimabaixa().toDate()));

        }
        else{

            if(pMapa.getUsuarioRef().getEmail().equals(pMeuUsuario)) cTxtTitulo.setText("Com Você Desde:");

            else cTxtTitulo.setText(String.format("Com %s Desde:", pMapa.getUsuarioRef().getNome()));

            if(pMapa.getEmprestimo() != null) cTxtInfo.setText(mDateFormat.format(pMapa.getEmprestimo().toDate()));

        }

        if(pMapa.getObservacao() != null){

            cLayoutUltimaObservacao.setVisibility(View.VISIBLE);
            cTxtUltimaObservacao.setText(pMapa.getObservacao());

        }
        else cLayoutUltimaObservacao.setVisibility(View.GONE);

        List<String> mObservacoes = pMapa.getObservacoes() == null ? new ArrayList<String>() : pMapa.getObservacoes();

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(pContext, android.R.layout.simple_list_item_1, android.R.id.text1, mObservacoes);

        cListObservacoes.setAdapter(mAdapter);

    }

    public void VisibilityObservacoes(){

        Drawable mFilterDraw = cFragment.getResources().getDrawable(R.drawable.ic_add);

        if(cLayoutLista.getVisibility() == View.VISIBLE) cLayoutLista.setVisibility(View.GONE);

        else {

            mFilterDraw = cFragment.getResources().getDrawable(R.drawable.ic_remove);

            cLayoutLista.setVisibility(View.VISIBLE);

        }

        mFilterDraw.setBounds( 0, 0, mFilterDraw.getIntrinsicWidth(), mFilterDraw.getIntrinsicHeight());

        cBtnMais.setForeground(mFilterDraw);

    }

}
