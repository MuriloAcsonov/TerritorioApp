package com.muriloacsonov.territorio.helper;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.muriloacsonov.territorio.R;
import com.muriloacsonov.territorio.model.Cadastro;
import com.muriloacsonov.territorio.model.Congregacao;

import java.util.ArrayList;
import java.util.List;

public class WelcomeHelper {

    private EditText txtNome;
    private EditText txtSenha;
    private Spinner spnCongregacao;
    private Spinner spnGrupo;
    private View view;

    private boolean fragment1 = true;

    private Cadastro cadastro;

    public WelcomeHelper(View fragment) {

        this.view = fragment;

        this.cadastro = new Cadastro();

        switch ((int) fragment.getTag()) {

            case 1:
                this.txtNome = (EditText) fragment.findViewById(R.id.edtNome);
                this.txtSenha = (EditText) fragment.findViewById(R.id.edtSenha);
                break;

            case 2:
                fragment1 = false;
                this.spnGrupo = (Spinner) fragment.findViewById(R.id.spnGrupo);
                this.spnCongregacao = (Spinner) fragment.findViewById(R.id.spnCongregacao);
                break;
        }

    }

    public boolean ValidarCampos() {

        boolean valido = true;

        if (fragment1) {

            if (this.txtNome.getText().toString().equals("")) {
                this.txtNome.setError("Este campo não pode ser vazio!");
                valido = false;
            }

            if (this.txtSenha.getText().toString().equals("")) {
                this.txtSenha.setError("Este campo não pode ser vazio!");
                valido = false;
            }

        }

        return valido;

    }

    public boolean CarregarCongregacoes(List<Congregacao> pCongregacoes) {

        boolean mRetorno = false;

        String[] mNomes = new String[pCongregacoes.size()];

        for (int i = 0; i < pCongregacoes.size(); i++) {
            mNomes[i] = pCongregacoes.get(i).getNome();
        }

        if (mNomes.length > 0) {

            try {

                ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, mNomes);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnCongregacao.setAdapter(arrayAdapter);

                mRetorno = true;

            }
            catch (Exception ex){

                Log.i("Spinner Congregacoes", ex.toString());

            }

        }

        return mRetorno;

    }

    public Cadastro getCadastro() {

        if (fragment1) {
            cadastro.setNome(this.txtNome.getText().toString());
            cadastro.setSenha(this.txtSenha.getText().toString());
        }

        return cadastro;

    }

}
