package com.grupo13.inventario;

public class Docente {
    int idDocente;
    String nomDocente;

    public Docente(){}

    public Docente(int idDocente, String nomDocente){
        this.idDocente=idDocente;
        this.nomDocente=nomDocente;
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
