package com.muriloacsonov.territorio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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

}
