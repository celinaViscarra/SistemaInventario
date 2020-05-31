package com.grupo13.inventario;

public class Dias {
    String diaCod, diaNombre;

    public Dias(){}

    public Dias(String dc, String dn){
        this.diaCod=dc;
        this.diaNombre=dn;
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
