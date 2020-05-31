package com.grupo13.inventario;

public class Marca {
    int idMarca;
    String nomMarca;

    public Marca(int id, String nom){
        this.idMarca=id;
        this.nomMarca=nom;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNomMarca() {
        return nomMarca;
    }

    public void setNomMarca(String nomMarca) {
        this.nomMarca = nomMarca;
    }
}
