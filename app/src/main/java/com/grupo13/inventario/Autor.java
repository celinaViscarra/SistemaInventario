package com.grupo13.inventario;

public class Autor {
    int idAutor;
    String nomAutor, apeAutor;

    public Autor(){}

    public Autor(int idAutor, String nomAutor, String apeAutor){
        this.idAutor = idAutor;
        this.nomAutor = nomAutor;
        this.apeAutor = apeAutor;
    }
    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNomAutor() {
        return nomAutor;
    }

    public void setNomAutor(String nomAutor) {
        this.nomAutor = nomAutor;
    }

    public String getApeAutor() {
        return apeAutor;
    }

    public void setApeAutor(String apeAutor) {
        this.apeAutor = apeAutor;
    }
}
