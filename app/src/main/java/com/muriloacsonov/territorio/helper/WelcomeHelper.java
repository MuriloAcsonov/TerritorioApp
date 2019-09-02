package com.muriloacsonov.territorio.helper;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.muriloacsonov.territorio.R;
import com.muriloacsonov.territorio.model.Cadastro;

public class WelcomeHelper {

    private EditText nome;
    private EditText senha;
    private Spinner congregacao;
    private Spinner grupo;
    private boolean fragment1 = true;

    private Cadastro cadastro;

    public WelcomeHelper(View fragment){

        this.cadastro = new Cadastro();

        switch ((int) fragment.getTag()){

            case 1:

                this.nome = (EditText) fragment.findViewById(R.id.edtNome);
                this.senha = (EditText) fragment.findViewById(R.id.edtSenha);
                this.congregacao = (Spinner) fragment.findViewById(R.id.spnCongregacao);

                break;

            case 2:

                this.grupo = (Spinner) fragment.findViewById(R.id.spnGrupo);
                fragment1 = false;

                break;
        }

    }

    public boolean ValidarCampos(){

        boolean valido = true;

        if(fragment1){

            if(this.nome.getText().toString().equals("")){
                this.nome.setError("Este campo não pode ser vazio!");
                valido = false;
            }

            if(this.senha.getText().toString().equals("")){
                this.senha.setError("Este campo não pode ser vazio!");
                valido = false;
            }

        }

        return valido;

    }

    public  Cadastro getCadastro(){

        if(fragment1){
            cadastro.setNome(this.nome.getText().toString());
            cadastro.setSenha(this.senha.getText().toString());
        }

        return cadastro;

    }

}
