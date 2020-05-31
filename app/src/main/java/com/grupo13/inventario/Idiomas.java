package com.grupo13.inventario;

public class Idiomas {
    int idIdioma;
    String nombreIdioma;

    public Idiomas(){}

    public Idiomas(int id, String nom){
        this.idIdioma = id;
        this.nombreIdioma = nom;
    }

    public int getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getNombreIdioma() {
        return nombreIdioma;
    }

    public void setNombreIdioma(String nombreIdioma) {
        this.nombreIdioma = nombreIdioma;
    }
}
