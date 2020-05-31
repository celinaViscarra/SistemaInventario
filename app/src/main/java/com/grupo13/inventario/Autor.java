package com.grupo13.inventario;

public class Autor {
    int idAutor;
    String nomAutor, apeAutor;

    public Autor(){}

    public Autor(int i, String n, String a){
        this.idAutor = i;
        this.nomAutor = n;
        this.apeAutor = a;
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
