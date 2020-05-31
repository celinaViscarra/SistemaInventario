package com.grupo13.inventario;

public class Categorias {
    int idCategoria;
    String nomCategoria;

    public Categorias(int id, String nom){
        this.idCategoria=id;
        this.nomCategoria=nom;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }
}
