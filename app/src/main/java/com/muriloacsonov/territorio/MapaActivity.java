package com.muriloacsonov.territorio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.muriloacsonov.territorio.model.Mapa;

public class MapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa);

        Mapa mMapa = (Mapa) getIntent().getSerializableExtra("mapa");
        int mTipo = (int) getIntent().getSerializableExtra("tipo");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_Mapa, new MapaFragmentInfo());

        fragmentTransaction.commit();

        switch (mTipo){

            case 0:

                break;

            case 1:

                break;

        }

    }

}
