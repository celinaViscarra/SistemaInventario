package com.grupo13.inventario.activities.AppLanguage;

public class CountryItem {
    private String nombreLenguaje;
    private int banderaLenguaje;

    public CountryItem(String nombreLenguaje, int banderaLenguaje) {
        this.nombreLenguaje = nombreLenguaje;
        this.banderaLenguaje = banderaLenguaje;
    }

    public CountryItem(){

    }

    public String getNombreLenguaje() {
        return nombreLenguaje;
    }

    public void setNombreLenguaje(String nombreLenguaje) {
        this.nombreLenguaje = nombreLenguaje;
    }

    public int getBanderaLenguaje() {
        return banderaLenguaje;
    }

    public void setBanderaLenguaje(int banderaLenguaje) {
        this.banderaLenguaje = banderaLenguaje;
    }
}
