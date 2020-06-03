package com.grupo13.inventario.modelo;

public class Categorias {
    int idCategoria;
    String nomCategoria;

    public Categorias(int idCategoria, String nomCategoria){
        this.idCategoria=idCategoria;
        this.nomCategoria=nomCategoria;
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
