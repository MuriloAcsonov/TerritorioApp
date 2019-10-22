package com.muriloacsonov.territorio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Mapa;

public class MapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa);

        Mapa mMapa = (Mapa) getIntent().getExtras().getParcelable("mapa");
        Dirigente mDirigente = (Dirigente) getIntent().getExtras().getParcelable("dirigente");
        int mTipo = (int) getIntent().getSerializableExtra("tipo");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (mTipo){

            case 0:

                Bundle bundle = new Bundle();
                bundle.putParcelable("mapa", mMapa);
                bundle.putParcelable("dirigente", mDirigente);

                MapaFragmentInfo mMapaFragmentInfo =  new MapaFragmentInfo();
                mMapaFragmentInfo.setArguments(bundle);

                fragmentTransaction.replace(R.id.frame_Mapa, mMapaFragmentInfo);

                break;

            case 1:

                break;

        }

        fragmentTransaction.commit();

    }

}
