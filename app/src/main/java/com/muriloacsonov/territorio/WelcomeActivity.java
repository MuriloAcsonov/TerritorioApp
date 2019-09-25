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

import com.muriloacsonov.territorio.model.Dirigente;

public class WelcomeActivity extends AppCompatActivity implements WelcomeFragment1.ConcluidoListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onConcluir(Dirigente dirigente, boolean concluiu) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


        if(concluiu){

            for(Fragment fragmentAlive : getSupportFragmentManager().getFragments()){

                getSupportFragmentManager().beginTransaction().remove(fragmentAlive).commit();

                Intent mainActivity = new Intent(this, MainActivity.class);

                mainActivity.putExtra("dirigente", dirigente);
                startActivity(mainActivity);
                finish();

            }

        }
        else {

            Bundle bundle = new Bundle();

            bundle.putSerializable("dirigente", dirigente);

            WelcomeFragment2 welcomeFragment2 = new WelcomeFragment2();
            welcomeFragment2.setArguments(bundle);

            fragmentTransaction.replace(R.id.frame_Welcome, welcomeFragment2);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

    }

}
