package com.muriloacsonov.territorio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.muriloacsonov.territorio.model.Mapa;

public class MapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa);

        Mapa mMapa = (Mapa) getIntent().getSerializableExtra("mapa");
        int mTipo = (int) getIntent().getSerializableExtra("tipo");

        switch (mTipo){

            case 0:

                break;

            case 1:

                break;

        }

    }

}
