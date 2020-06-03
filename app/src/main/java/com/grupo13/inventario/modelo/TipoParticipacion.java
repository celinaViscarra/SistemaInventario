package com.grupo13.inventario.modelo;

public class TipoParticipacion {
    int idParticipacion;
    String nomParticipacion;

    public  TipoParticipacion(){}

    public  TipoParticipacion(int id, String nom){
        this.idParticipacion=id;
        this.nomParticipacion=nom;
    }

    public int getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(int idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public String getNomParticipacion() {
        return nomParticipacion;
    }

    public void setNomParticipacion(String nomParticipacion) {
        this.nomParticipacion = nomParticipacion;
    }
}
