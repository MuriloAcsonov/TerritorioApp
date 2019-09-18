package com.muriloacsonov.territorio.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muriloacsonov.territorio.model.Congregacao;

import java.util.ArrayList;
import java.util.List;

public class CongregacaoFs {

    private FirebaseFirestore firestore;

    public CongregacaoFs() {

        firestore = FirebaseFirestore.getInstance();

    }

    public List<Congregacao> getCongregacoes() {

        final List<Congregacao> mCongregacoes = new ArrayList<Congregacao>();

        firestore.collection("congregacao").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mCongregacoes.add(document.toObject(Congregacao.class));
                        Log.i("Get Congregacoes", "Congregacoes geradas com sucesso");
                    }
                }
            }
        });

        return mCongregacoes;
    }

}
