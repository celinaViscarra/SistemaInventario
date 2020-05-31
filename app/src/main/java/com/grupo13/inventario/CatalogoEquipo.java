package com.grupo13.inventario;

public class CatalogoEquipo {
    String idCatalogo, modeloEquipo;
    int memoria, cantEquipo;

    public CatalogoEquipo(String id, String modelo, int m, int cant){
        this.idCatalogo=id;
        this.modeloEquipo=modelo;
        this.memoria=m;
        this.cantEquipo=cant;
    }

    public String getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(String idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getModeloEquipo() {
        return modeloEquipo;
    }

    public void setModeloEquipo(String modeloEquipo) {
        this.modeloEquipo = modeloEquipo;
    }

    public int getMemoria() {
        return memoria;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

    public int getCantEquipo() {
        return cantEquipo;
    }

    public void setCantEquipo(int cantEquipo) {
        this.cantEquipo = cantEquipo;
    }
}
