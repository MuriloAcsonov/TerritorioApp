package com.muriloacsonov.territorio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muriloacsonov.territorio.firebase.CongregacaoFs;
import com.muriloacsonov.territorio.helper.WelcomeHelper;
import com.muriloacsonov.territorio.model.Cadastro;
import com.muriloacsonov.territorio.model.Congregacao;

import java.util.ArrayList;
import java.util.List;

public class WelcomeFragment2 extends Fragment implements View.OnClickListener  {

    private List<Congregacao> mCongregacoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_welcome2, container, false);
        mView.setTag(2);

        loadCongregacoes(mView);

        Button concluir = (Button) mView.findViewById(R.id.btConcluir);

        concluir.setOnClickListener(this);

        Bundle bundle = getArguments();

        Cadastro cadastro = (Cadastro) bundle.getSerializable("cadastro");

        final Spinner spnCongregacao = (Spinner) mView.findViewById(R.id.spnCongregacao);
        final Spinner spnGrupo = (Spinner) mView.findViewById(R.id.spnGrupo);

        spnCongregacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                    Congregacao mCongregacao = mCongregacoes.get(position);

                    if (mCongregacao.getGrupos() == null){
                        spnGrupo.setVisibility(View.INVISIBLE);
                        spnGrupo.setAdapter(null);
                    }
                    else{
                        WelcomeHelper welcomeHelper = new WelcomeHelper(getView());
                        welcomeHelper.CarregarGrupos(mCongregacao.getGrupos());
                        spnGrupo.setVisibility(View.VISIBLE);
                    }

            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                spnGrupo.setVisibility(View.INVISIBLE);
            }
        });

        return mView;
    }

    private void loadCongregacoes(final View pView){

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("congregacao").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    mCongregacoes = new ArrayList<Congregacao>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mCongregacoes.add(document.toObject(Congregacao.class));
                        Log.i("Get Congregacoes", "Congregacoes geradas com sucesso");
                    }

                    if(mCongregacoes.size() > 0){

                        WelcomeHelper welcomeHelper = new WelcomeHelper(pView);

                        welcomeHelper.CarregarCongregacoes(mCongregacoes);

                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view){

        switch (view.getId()){

            case R.id.btConcluir:

                ((WelcomeActivity) getActivity()).onConcluir(null, true);

                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
