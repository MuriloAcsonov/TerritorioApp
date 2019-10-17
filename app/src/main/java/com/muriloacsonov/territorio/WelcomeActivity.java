package com.muriloacsonov.territorio;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.muriloacsonov.territorio.model.Congregacao;
import com.muriloacsonov.territorio.model.Dirigente;

public class WelcomeActivity extends AppCompatActivity implements WelcomeFragment1.ConcluidoListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_Welcome, new WelcomeFragment1());

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onConcluir(Dirigente pDirigente, Congregacao pCongregacao, boolean concluiu) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


        if(concluiu){

            for(Fragment fragmentAlive : getSupportFragmentManager().getFragments()){

                getSupportFragmentManager().beginTransaction().remove(fragmentAlive).commit();

                Intent mainActivity = new Intent(this, MainActivity.class);

                mainActivity.putExtra("dirigente", pDirigente);

                if(pCongregacao != null) {
                    mainActivity.putExtra("congregacao", pCongregacao);
                }

                startActivity(mainActivity);
                finish();

            }

        }
        else {

            Bundle bundle = new Bundle();

            bundle.putParcelable("dirigente", pDirigente);

            WelcomeFragment2 welcomeFragment2 = new WelcomeFragment2();
            welcomeFragment2.setArguments(bundle);

            fragmentTransaction.replace(R.id.frame_Welcome, welcomeFragment2);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

    }

}
