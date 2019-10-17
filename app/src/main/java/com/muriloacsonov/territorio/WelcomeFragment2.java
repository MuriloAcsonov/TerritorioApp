package com.muriloacsonov.territorio;

import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muriloacsonov.territorio.helper.WelcomeHelper;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Congregacao;

import java.util.ArrayList;
import java.util.List;

public class WelcomeFragment2 extends Fragment implements View.OnClickListener  {

    private List<Congregacao> mCongregacoes;

    private Congregacao mCongregacao;

    private Dirigente cDirigente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_welcome2, container, false);
        mView.setTag(2);

        loadCongregacoes(mView);

        Button concluir = (Button) mView.findViewById(R.id.btConcluir);

        concluir.setOnClickListener(this);

        Bundle bundle = getArguments();

        Dirigente dirigente = (Dirigente) bundle.getParcelable("dirigente");

        cDirigente = (Dirigente) bundle.getParcelable("dirigente");

        final Spinner spnCongregacao = (Spinner) mView.findViewById(R.id.spnCongregacao);
        final Spinner spnGrupo = (Spinner) mView.findViewById(R.id.spnGrupo);

        spnCongregacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                    mCongregacao = mCongregacoes.get(position);

                    if (mCongregacao.getGrupos() == null){
                        spnGrupo.setVisibility(View.INVISIBLE);
                        spnGrupo.setAdapter(null);
                    }
                    else{
                        WelcomeHelper welcomeHelper = new WelcomeHelper(getView());
                        welcomeHelper.CarregarGrupos(mCongregacao.getGrupos());
                        spnGrupo.setVisibility(View.VISIBLE);

                        ////TESTE

                        cDirigente.setAdm(true);
                        cDirigente.setCongregacao(mCongregacao.getId());
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

        firestore.collection("congregacao").orderBy("criacao", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    mCongregacoes = new ArrayList<Congregacao>();

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Congregacao mCongregacao = document.toObject(Congregacao.class);
                        mCongregacao.setId(document.getId());
                        mCongregacoes.add(mCongregacao);

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

                ((WelcomeActivity) getActivity()).onConcluir(cDirigente, mCongregacao, true);

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
