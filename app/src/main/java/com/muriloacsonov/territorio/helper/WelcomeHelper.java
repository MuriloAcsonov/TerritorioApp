package com.muriloacsonov.territorio.helper;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.muriloacsonov.territorio.R;
import com.muriloacsonov.territorio.model.Dirigente;
import com.muriloacsonov.territorio.model.Congregacao;

import java.util.List;

public class WelcomeHelper {

    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtSenha;
    private Spinner spnCongregacao;
    private Spinner spnGrupo;
    private View view;

    private boolean fragment1 = true;

    private Dirigente dirigente;

    public WelcomeHelper(View fragment) {

        this.view = fragment;

        switch ((int) fragment.getTag()) {

            case 1:
                this.txtNome = (EditText) fragment.findViewById(R.id.edtNome);
                this.txtEmail = (EditText) fragment.findViewById(R.id.edtEmail);
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

                ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), R.layout.spinner_welcome_item, mNomes);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_welcome_dropdown_item);

                spnCongregacao.setAdapter(arrayAdapter);

                mRetorno = true;

            }
            catch (Exception ex){

                Log.i("Spinner Congregacoes", ex.toString());

            }

        }

        return mRetorno;

    }

    public boolean CarregarGrupos(List<String> pGrupos){

        boolean mRetorno = false;

        try {

            ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), R.layout.spinner_welcome_item, pGrupos);
            arrayAdapter.setDropDownViewResource(R.layout.spinner_welcome_dropdown_item);

            spnGrupo.setAdapter(arrayAdapter);

            mRetorno = true;

        }
        catch (Exception ex){

            Log.i("Spinner Grupos", ex.toString());

        }

        return mRetorno;

    }

    public Dirigente getDirigenteLogin() {

        if (fragment1) {

            this.dirigente = new Dirigente(this.txtNome.getText().toString(), null, this.txtEmail.getText().toString(), this.txtSenha.getText().toString(), -1, false);

        }

        return dirigente;

    }

}
