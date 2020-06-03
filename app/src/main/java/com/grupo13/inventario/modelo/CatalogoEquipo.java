package com.grupo13.inventario.modelo;

public class CatalogoEquipo {
    String idCatalogo, modeloEquipo;
    int memoria, cantEquipo;

    public CatalogoEquipo(String idCatalogo, String modeloEquipo, int memoria, int cantEquipo){
        this.idCatalogo=idCatalogo;
        this.modeloEquipo=modeloEquipo;
        this.memoria=memoria;
        this.cantEquipo=cantEquipo;
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
