package com.grupo13.inventario;

public class Docente {
    int idDocente;
    String nomDocente;

    public Docente(){}

    public Docente(int id, String nom){
        this.idDocente=id;
        this.nomDocente=nom;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getNomDocente() {
        return nomDocente;
    }

    public void setNomDocente(String nomDocente) {
        this.nomDocente = nomDocente;
    }
}
