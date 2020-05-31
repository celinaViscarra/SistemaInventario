package com.grupo13.inventario;

public class TipoProducto {
    int idTipoProducto;
    String nomTipoProducto;

    public TipoProducto(){}

    public TipoProducto(int id, String nom){
        this.idTipoProducto=id;
        this.nomTipoProducto=nom;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getNomTipoProducto() {
        return nomTipoProducto;
    }

    public void setNomTipoProducto(String nomTipoProducto) {
        this.nomTipoProducto = nomTipoProducto;
    }
}
