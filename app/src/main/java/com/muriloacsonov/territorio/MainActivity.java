package com.muriloacsonov.territorio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muriloacsonov.territorio.helper.MainHelper;
import com.muriloacsonov.territorio.model.Congregacao;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Dirigente cDirigente;
    Congregacao cCongregacao;

    List<Mapa> cMapas;

    Button ftFechado, ftMeusMapas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ftFechado = (Button) findViewById(R.id.ftRecorrencia);
        ftFechado.setOnClickListener(this);
        ftMeusMapas = (Button) findViewById(R.id.ftDisponivel);
        ftMeusMapas.setOnClickListener(this);

        Dirigente mDirigente = (Dirigente) getIntent().getSerializableExtra("dirigente");
        Congregacao mCongregacao = (Congregacao) getIntent().getSerializableExtra("congregacao");

        cDirigente = mDirigente;
        cCongregacao = mCongregacao;

        loadMaps();

    }

    int recorrenciaClicks = 0;

    private void RecorrenciaClick(Button button){

        switch (recorrenciaClicks){

            case 0:

                Drawable filterDraw = getResources().getDrawable(R.drawable.ic_arrow_down);
                filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

                button.setCompoundDrawables(null, null, filterDraw, null);
                button.setBackgroundTintList(getResources().getColorStateList(R.color.lightpurple));
                button.setTextColor(getResources().getColorStateList(R.color.white));

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
                button.setBackgroundTintList(getResources().getColorStateList(R.color.filtersmain));
                button.setTextColor(getResources().getColorStateList(R.color.black));

                recorrenciaClicks = 0;

                break;



        }

    }

    private void loadMaps(){

        cMapas = new ArrayList<Mapa>();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("congregacao/"+cDirigente.getCongregacao()+"/mapa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        final Mapa mMapa = document.toObject(Mapa.class);

                        mMapa.setId(document.getId());

                        mMapa.setNmGrupo(cCongregacao.getGrupos().get(mMapa.getGrupo()));

                        DocumentReference documentReference = document.getDocumentReference("usuarioRef");
                        if(documentReference != null){

                            Task<DocumentSnapshot> taskDirigente = documentReference.get();

                            taskDirigente.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Dirigente mDirigente = task.getResult().toObject(Dirigente.class);
                                    mMapa.setUsuarioRef(mDirigente);
                                    cMapas.add(mMapa);

                                    MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());

                                    mainHelper.CarregarListaMapas(cMapas, null);
                                }

                            });

                        }
                        else {
                            cMapas.add(mMapa);
                        }

                    }

                    if(cMapas.size() > 0){

                        MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());

                        mainHelper.CarregarListaMapas(cMapas, null);

                    }

                }
            }
        });

    }

    private void loadMyMaps(){

        List<Mapa> mMapas = new ArrayList<Mapa>();

        for(Mapa mapa : cMapas){

            if(mapa.getUsuarioRef() != null){

                if(mapa.getUsuario().equals(cDirigente.getEmail())){
                    mMapas.add(mapa);
                }
            }

        }

        if(mMapas.size() > 0){

            MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());
            mainHelper.CarregarListaMapas(mMapas, null);

        }



    }

    Boolean cMeusMapas = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ftRecorrencia:

                RecorrenciaClick((Button)v);

                break;

            case R.id.ftDisponivel:

                if(cMeusMapas){
                    cMeusMapas = false;
                    ftMeusMapas.setBackgroundTintList(getResources().getColorStateList(R.color.filtersmain));
                    ftMeusMapas.setTextColor(getResources().getColorStateList(R.color.black));
                    MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());
                    mainHelper.CarregarListaMapas(cMapas, null);
                }
                else {
                    cMeusMapas = true;
                    ftMeusMapas.setBackgroundTintList(getResources().getColorStateList(R.color.lightpurple));
                    ftMeusMapas.setTextColor(getResources().getColorStateList(R.color.white));
                    loadMyMaps();
                }

                break;
        }
    }
}
