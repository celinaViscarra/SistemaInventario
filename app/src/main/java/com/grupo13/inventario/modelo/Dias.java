package com.grupo13.inventario.modelo;

public class Dias {
    String diaCod, diaNombre;

    public Dias(){}

    public Dias(String diaCod, String diaNombre){
        this.diaCod=diaCod;
        this.diaNombre=diaNombre;
    }

    public String getDiaCod() {
        return diaCod;
    }

    public void setDiaCod(String diaCod) {
        this.diaCod = diaCod;
    }

    public String getDiaNombre() {
        return diaNombre;
    }

    public void setDiaNombre(String diaNombre) {
        this.diaNombre = diaNombre;
    }
}
