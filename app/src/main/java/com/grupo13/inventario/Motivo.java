package com.grupo13.inventario;

public class Motivo {
    int idMotivo;
    String nomMotivo;

    public Motivo(){}

    public Motivo(int id, String nom){
        this.idMotivo=id;
        this.nomMotivo=nom;
    }

    public int getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(int idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getNomMotivo() {
        return nomMotivo;
    }

    public void setNomMotivo(String nomMotivo) {
        this.nomMotivo = nomMotivo;
    }
}
