package com.muriloacsonov.territorio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muriloacsonov.territorio.helper.MainHelper;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Mapa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Dirigente cDirigente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button ftRecorrencia = (Button) findViewById(R.id.ftRecorrencia);

        ftRecorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RecorrenciaClick(ftRecorrencia);

            }
        });

        Dirigente mDirigente = (Dirigente) getIntent().getSerializableExtra("dirigente");

        cDirigente = mDirigente;

        loadMaps();

    }

    int recorrenciaClicks = 0;

    private void RecorrenciaClick(Button button){

        switch (recorrenciaClicks){

            case 0:

                Drawable filterDraw = getResources().getDrawable(R.drawable.ic_arrow_down);
                filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

                button.setCompoundDrawables(null, null, filterDraw, null);

                recorrenciaClicks++;

                break;

            case 1:

                filterDraw = getResources().getDrawable(R.drawable.ic_arrow_up);
                filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

                button.setCompoundDrawables(null, null, filterDraw, null);

                recorrenciaClicks++;

                break;

            case 2:

                filterDraw = getResources().getDrawable(R.drawable.ic_filter_du);
                filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

                button.setCompoundDrawables(null, null, filterDraw, null);

                recorrenciaClicks = 0;

                break;



        }

    }

    private void loadMaps(){

        final List<Mapa> mMapas = new ArrayList<Mapa>();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("congregacao/"+cDirigente.getCongregacao()+"/mapa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Mapa mMapa = document.toObject(Mapa.class);
                        mMapa.setId(document.getId());

                        mMapas.add(mMapa);

                    }

                    if(mMapas.size() > 0){

                        MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());

                        mainHelper.CarregarListaMapas(mMapas, null);

                    }

                }
            }
        });

    }

}
