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

    List<Mapa> cMapas, cMapasFiltro;

    Boolean cMeusMapas = false, cEmUso = false, cGrupo = false;
    int recorrenciaClicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ftFechado = (Button) findViewById(R.id.ftRecorrencia);
         ftFechado.setOnClickListener(this);
        Button ftMeusMapas = (Button) findViewById(R.id.ftDisponivel);
         ftMeusMapas.setOnClickListener(this);
        Button ftGrupo = (Button) findViewById(R.id.ftGrupo);
        ftGrupo.setOnClickListener(this);

        Dirigente mDirigente = (Dirigente) getIntent().getSerializableExtra("dirigente");
        Congregacao mCongregacao = (Congregacao) getIntent().getSerializableExtra("congregacao");
        cDirigente = mDirigente;
        cCongregacao = mCongregacao;

        if(mDirigente.getAdm()){
            Button ftEmUso = (Button) findViewById(R.id.ftEmUso);
            ftEmUso.setOnClickListener(this);
        }

        loadMaps();

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

                                    mainHelper.CarregarListaMapas(cMapas);

                                    cMapasFiltro.addAll(cMapas);

                                }

                            });

                        }
                        else {
                            cMapas.add(mMapa);
                        }

                    }

                    if(cMapas.size() > 0){

                        MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());

                        mainHelper.CarregarListaMapas(cMapas);

                    }

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ftRecorrencia:

                switch (recorrenciaClicks){

                    case 0:

                        Drawable filterDraw = getResources().getDrawable(R.drawable.ic_arrow_down);
                        filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

                        ((Button)v).setCompoundDrawables(null, null, filterDraw, null);
                        ((Button)v).setBackgroundTintList(getResources().getColorStateList(R.color.lightpurple));
                        ((Button)v).setTextColor(getResources().getColorStateList(R.color.white));

                        List<Mapa> mMapas = new ArrayList<Mapa>();
                        mMapas.addAll(cMapas);
                        mMapas = MainHelper.ultimabaixaFiltro(mMapas, recorrenciaClicks);
                        MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());
                        mainHelper.CarregarListaMapas(mMapas);

                        recorrenciaClicks++;

                        break;

                    case 1:

                        filterDraw = getResources().getDrawable(R.drawable.ic_arrow_up);
                        filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

                        ((Button)v).setCompoundDrawables(null, null, filterDraw, null);

                        mMapas = new ArrayList<Mapa>();
                        mMapas.addAll(cMapas);
                        mMapas = MainHelper.ultimabaixaFiltro(mMapas, recorrenciaClicks);
                        mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());
                        mainHelper.CarregarListaMapas(mMapas);

                        recorrenciaClicks++;

                        break;

                    case 2:

                        filterDraw = getResources().getDrawable(R.drawable.ic_filter_du);
                        filterDraw.setBounds( 0, 0, filterDraw.getIntrinsicWidth(), filterDraw.getIntrinsicHeight() );

                        ((Button)v).setCompoundDrawables(null, null, filterDraw, null);
                        ((Button)v).setBackgroundTintList(getResources().getColorStateList(R.color.filtersmain));
                        ((Button)v).setTextColor(getResources().getColorStateList(R.color.black));

                        mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());
                        mainHelper.CarregarListaMapas(cMapas);

                        recorrenciaClicks = 0;

                        break;

                }

                break;

            case R.id.ftDisponivel:

                if(cMeusMapas){
                    cMeusMapas = false;
                    ((Button)v).setBackgroundTintList(getResources().getColorStateList(R.color.filtersmain));
                    ((Button)v).setTextColor(getResources().getColorStateList(R.color.black));

                    if(cGrupo){

                    }

                    MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());
                    mainHelper.CarregarListaMapas(cMapas);
                }
                else {
                    cMeusMapas = true;
                    ((Button)v).setBackgroundTintList(getResources().getColorStateList(R.color.lightpurple));
                    ((Button)v).setTextColor(getResources().getColorStateList(R.color.white));
                    MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());
                    //mainHelper.CarregarListaMapas(mMapas);
                }

                break;
        }
    }
}
