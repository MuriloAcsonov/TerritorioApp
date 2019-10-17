package com.muriloacsonov.territorio.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.List;

public class Mapa implements Parcelable {

    private String id;
    private int grupo;
    private String imagem;
    private Timestamp ultimabaixa;
    private String nmGrupo;
    private List<String> observacoes;
    private String observacao;
    private String usuario;
    @Exclude
    private Dirigente usuarioRef;

    //GETTERS && SETTERS

    public Mapa(){}

    public Mapa(String pId, int pGrupo, String pImage, Timestamp pUltimaBaixa, String pnmGrupo, List<String> pObservacoes, String pObservacao, String pUsuario, Dirigente pUsuarioRef){

        id = pId;
        grupo = pGrupo;
        imagem = pImage;
        ultimabaixa = pUltimaBaixa;
        nmGrupo = pnmGrupo;
        observacoes = pObservacoes;
        observacao = pObservacao;
        usuario = pUsuario;
        usuarioRef = pUsuarioRef;

    }

    protected Mapa(Parcel pParcel){

        id = pParcel.readString();
        grupo = pParcel.readInt();
        imagem = pParcel.readString();
        ultimabaixa = pParcel.readParcelable(Timestamp.class.getClassLoader());
        nmGrupo = pParcel.readString();
        pParcel.readList(observacoes, String.class.getClassLoader());
        observacao = pParcel.readString();
        usuario = pParcel.readString();
        usuarioRef = pParcel.readParcelable(Dirigente.class.getClassLoader());

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Timestamp getUltimabaixa() {
        return ultimabaixa;
    }

    public void setUltimabaixa(Timestamp ultimabaixa) {
        this.ultimabaixa = ultimabaixa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNmGrupo() {
        return nmGrupo;
    }

    public void setNmGrupo(String nmGrupo) {
        this.nmGrupo = nmGrupo;
    }

    public List<String> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<String> observacoes) {
        this.observacoes = observacoes;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Exclude
    public Dirigente getUsuarioRef() {
        return usuarioRef;
    }

    @Exclude
    public void setUsuarioRef(Dirigente usuarioRef) {
        this.usuarioRef = usuarioRef;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeInt(grupo);
        dest.writeString(imagem);
        dest.writeParcelable(ultimabaixa, flags);
        dest.writeString(nmGrupo);
        dest.writeList(observacoes);
        dest.writeString(observacao);
        dest.writeString(usuario);
        dest.writeParcelable(usuarioRef, flags);

    }

    public static final Parcelable.Creator<Mapa>
            CREATOR = new Parcelable.Creator<Mapa>() {

        public Mapa createFromParcel(Parcel pParcel) {
            return new Mapa(pParcel);
        }

        public Mapa[] newArray(int size) {
            return new Mapa[size];
        }
    };

}