package com.muriloacsonov.territorio;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.muriloacsonov.territorio.helper.WelcomeHelper;
import com.muriloacsonov.territorio.model.Cadastro;

public class WelcomeFragment1 extends Fragment implements View.OnClickListener {

    ConcluidoListener concluidoListener;

    public interface ConcluidoListener{
        public void onConcluir(Cadastro cadastro, boolean concluiu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            concluidoListener = (ConcluidoListener) context;
        }
        catch (ClassCastException classException){
            Toast.makeText(context, "Não vai ser possivel avançar no momento", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome1, container, false);
        view.setTag(1);

        Button avancar = (Button) view.findViewById(R.id.btAvancar);

        avancar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){

        switch (view.getId()){

            case R.id.btAvancar:

                WelcomeHelper welcomeHelper = new WelcomeHelper(this.getView());

                boolean valido = welcomeHelper.ValidarCampos();

                if(valido){

                    Cadastro cadastro = welcomeHelper.getCadastro();

                    concluidoListener.onConcluir(cadastro, false);

                }

                break;
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();

        concluidoListener = null;

    }

}
