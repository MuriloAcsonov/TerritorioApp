package com.muriloacsonov.territorio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Internal;
import com.muriloacsonov.territorio.adapter.MapaAdapter;
import com.muriloacsonov.territorio.helper.MainHelper;
import com.muriloacsonov.territorio.model.Congregacao;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Filtros;
import com.muriloacsonov.territorio.model.Mapa;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MapaAdapter.onMapItemListener {

    Dirigente cDirigente;
    Congregacao cCongregacao;

    List<Mapa> cMapas, cMapasFiltro;

    Boolean cMeusMapas = false, cEmUso = false, cGrupo = false, cFechado = false;
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

        RecyclerView mListMaps = (RecyclerView) findViewById(R.id.rvwMapas);



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
        cMapasFiltro = new ArrayList<Mapa>();

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

                                    mainHelper.CarregarListaMapas(cMapas, MainActivity.this);

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

                        mainHelper.CarregarListaMapas(cMapas, MainActivity.this);

                    }

                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        Filtros mFiltro = new Filtros();
        mFiltro.setMapas(cMapas);
        mFiltro.setMapasFiltro(cMapasFiltro);
        mFiltro.setStEmUso(cEmUso);
        mFiltro.setStGrupo(cGrupo);
        mFiltro.setStMeusMapas(cMeusMapas);
        mFiltro.setDirigente(cDirigente);
        mFiltro.setOrderBy(recorrenciaClicks);
        mFiltro.setStFechado(cFechado);

        switch (v.getId()){

            case R.id.ftRecorrencia:

                cFechado = true;

                if(recorrenciaClicks == 2){
                    cFechado = false;
                    recorrenciaClicks = 0;
                }
                else{
                    recorrenciaClicks++;
                }

                MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());
                List<Mapa> mMapas = mainHelper.aplicarFiltros(mFiltro);
                mainHelper.CarregarListaMapas(mMapas, MainActivity.this);

                break;

            case R.id.ftDisponivel:

                mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());

                if(cMeusMapas){
                    mMapas = mainHelper.aplicarFiltros(mFiltro);
                    cMeusMapas = false;
                }
                else {
                    mMapas = mainHelper.aplicarFiltros(mFiltro);
                    cMeusMapas = true;
                }

                mainHelper.CarregarListaMapas(mMapas, MainActivity.this);

                break;

        }
    }

    @Override
    public void onMapClick(int pPosition) {

        Mapa mMapa;

        if(cMeusMapas || cEmUso || cGrupo || cFechado || recorrenciaClicks > 0){

            mMapa = cMapasFiltro.get(pPosition);

        }
        else {

            mMapa = cMapas.get(pPosition);

        }

    }
}
