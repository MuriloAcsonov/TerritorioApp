package com.muriloacsonov.territorio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.muriloacsonov.territorio.model.Cadastro;

public class WelcomeFragment2 extends Fragment implements View.OnClickListener  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome2, container, false);
        view.setTag(2);

        Button concluir = (Button) view.findViewById(R.id.btConcluir);

        concluir.setOnClickListener(this);

        Bundle bundle = getArguments();

        Cadastro cadastro = (Cadastro) bundle.getSerializable("cadastro");

        return view;
    }

    @Override
    public void onClick(View view){

        switch (view.getId()){

            case R.id.btConcluir:

                ((WelcomeActivity) getActivity()).onConcluir(null, true);

                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
