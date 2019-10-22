package com.muriloacsonov.territorio;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.muriloacsonov.territorio.helper.MapaHelper;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Mapa;

public class MapaFragmentInfo extends Fragment implements View.OnClickListener {

    MapaHelper cHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mapa_info, container, false);
        view.setTag(1);

        Bundle mBundle = getArguments();

        Dirigente mDirigente = (Dirigente) mBundle.getParcelable("dirigente");
        Mapa mMapa = (Mapa) mBundle.getParcelable("mapa");

        cHelper = new MapaHelper(view);
        cHelper.CarregarInformações(mMapa, mDirigente.getEmail(), view.getContext());

        Button mBtnMais = (Button) view.findViewById(R.id.btMaisObservacoes);
        mBtnMais.setOnClickListener(this);

        ConstraintLayout mLayoutMais = (ConstraintLayout) view.findViewById(R.id.lytObservacoesMapa);
        mLayoutMais.setOnClickListener(this);

        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btMaisObservacoes:
            case R.id.lytObservacoesMapa:
                cHelper.VisibilityObservacoes();
                break;

        }

    }

}
