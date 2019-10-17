package com.muriloacsonov.territorio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muriloacsonov.territorio.adapter.MapaAdapter;
import com.muriloacsonov.territorio.helper.MainHelper;
import com.muriloacsonov.territorio.model.Congregacao;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Filtros;
import com.muriloacsonov.territorio.model.Mapa;

import java.security.DomainLoadStoreParameter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MapaAdapter.onMapItemListener {

    Dirigente cDirigente;
    Congregacao cCongregacao;

    List<Mapa> cMapas, cMapasFiltro;

    Filtros cFiltro;

    Boolean cMeusMapas = false, cEmUso = false, cFechado = false;
    int recorrenciaClicks = 0, indexGrupo = -1;

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

        Dirigente mDirigente = (Dirigente) getIntent().getExtras().getParcelable("dirigente");
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

        cFiltro = new Filtros();
        cFiltro.setMapas(cMapas);
        cFiltro.setMapasFiltro(cMapasFiltro);
        cFiltro.setStEmUso(cEmUso);
        cFiltro.setGrupo(indexGrupo);
        cFiltro.setStMeusMapas(cMeusMapas);
        cFiltro.setDirigente(cDirigente);
        cFiltro.setOrderBy(recorrenciaClicks);
        cFiltro.setStFechado(cFechado);

        final MainHelper mainHelper = new MainHelper(MainActivity.this, cDirigente.getAdm());

        switch (v.getId()){

            case R.id.ftRecorrencia:

                cFechado = true;
                cFiltro.setStFechado(cFechado);

                if(recorrenciaClicks == 2){
                    cFechado = false;
                    cFiltro.setStFechado(cFechado);
                    recorrenciaClicks = 0;
                }
                else{
                    recorrenciaClicks++;
                }

                cMapasFiltro = mainHelper.aplicarFiltros(cFiltro);
                mainHelper.CarregarListaMapas(cMapasFiltro, MainActivity.this);

                break;

            case R.id.ftDisponivel:

                if(cMeusMapas){
                    cMeusMapas = false;
                    cFiltro.setStMeusMapas(cMeusMapas);
                    cMapasFiltro = mainHelper.aplicarFiltros(cFiltro);
                    mainHelper.CarregarListaMapas(cMapasFiltro, MainActivity.this);
                }
                else {
                    cMeusMapas = true;
                    cFiltro.setStMeusMapas(cMeusMapas);
                    cMapasFiltro = mainHelper.aplicarFiltros(cFiltro);
                    mainHelper.CarregarListaMapas(cMapasFiltro, MainActivity.this);
                }

                break;

            case R.id.ftEmUso:

                if(cEmUso){
                    cEmUso = false;
                    cFiltro.setStEmUso(cEmUso);
                    cMapasFiltro = mainHelper.aplicarFiltros(cFiltro);
                    mainHelper.CarregarListaMapas(cMapasFiltro, MainActivity.this);
                }
                else{
                    cEmUso = true;
                    cFiltro.setStEmUso(cEmUso);
                    cMapasFiltro = mainHelper.aplicarFiltros(cFiltro);
                    mainHelper.CarregarListaMapas(cMapasFiltro, MainActivity.this);
                }

                break;

            case R.id.ftGrupo:

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Escolha o grupo");
                builder.setCancelable(false);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        cFiltro.setGrupo(indexGrupo);
                        cMapasFiltro = mainHelper.aplicarFiltros(cFiltro);
                        mainHelper.CarregarListaMapas(cMapasFiltro, MainActivity.this);

                    }
                });

                builder.setNegativeButton("Limpar Filtro", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        indexGrupo = -1;
                        cFiltro.setGrupo(indexGrupo);
                        cMapasFiltro = mainHelper.aplicarFiltros(cFiltro);
                        mainHelper.CarregarListaMapas(cMapasFiltro, MainActivity.this);

                    }
                });

                String[] mGrupos = cCongregacao.getGrupos().toArray(new String[cCongregacao.getGrupos().size()]);

                builder.setSingleChoiceItems(mGrupos, indexGrupo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pIndexGrupo) {
                        indexGrupo = pIndexGrupo;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                break;

        }
    }

    @Override
    public void onMapClick(int pPosition) {

        Mapa mMapa = cMapas.get(pPosition);

        if(cMeusMapas || cEmUso || cFechado || recorrenciaClicks > 0){

            mMapa = cMapasFiltro.get(pPosition);

        }

        Log.i("MAPA-ID-CLICADO", mMapa.getId());

        Intent mMapaAct = new Intent(this, MapaActivity.class);

        mMapaAct.putExtra("mapa", mMapa);
        mMapaAct.putExtra("tipo", 0);

        startActivity(mMapaAct);

    }

}
