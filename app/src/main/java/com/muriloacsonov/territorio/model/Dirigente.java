package com.muriloacsonov.territorio.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Dirigente implements Parcelable {

    private String nome;
    private String congregacao;
    private String email;
    private String senha;
    private int grupo;
    private Boolean adm;

    public Dirigente(){}

    public Dirigente(String pNome, String pCongregacao, String pEmail, String pSenha, int pGrupo, boolean pAdm){

        nome = pNome;
        congregacao = pCongregacao;
        email = pEmail;
        senha = pSenha;
        grupo = pGrupo;
        adm = pAdm;

    }

    protected Dirigente(Parcel pParcel){

        nome = pParcel.readString();
        congregacao = pParcel.readString();
        email = pParcel.readString();
        senha = pParcel.readString();
        grupo = pParcel.readInt();
        adm = pParcel.readBoolean();

    }

    //Getters and Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(String congregacao) {
        this.congregacao = congregacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public Boolean getAdm() {
        return adm;
    }

    public void setAdm(Boolean adm) {
        this.adm = adm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nome);
        dest.writeString(congregacao);
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeInt(grupo);
        dest.writeBoolean(adm);

    }

    public static final Parcelable.Creator<Dirigente>
            CREATOR = new Parcelable.Creator<Dirigente>() {

        public Dirigente createFromParcel(Parcel pParcel) {
            return new Dirigente(pParcel);
        }

        public Dirigente[] newArray(int size) {
            return new Dirigente[size];
        }
    };

}
