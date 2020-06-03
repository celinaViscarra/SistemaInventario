package com.grupo13.inventario.modelo;

public class Ubicacion {
    int idUbicacion;
    String nomUbicacion;

    public Ubicacion(){}

    public Ubicacion(int id, String nom){
        this.idUbicacion=id;
        this.nomUbicacion=nom;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getNomUbicacion() {
        return nomUbicacion;
    }

    public void setNomUbicacion(String nomUbicacion) {
        this.nomUbicacion = nomUbicacion;
    }
}
