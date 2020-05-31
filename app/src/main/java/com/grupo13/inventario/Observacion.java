package com.grupo13.inventario;

public class Observacion {
    int cantSupuesta, cantReal;

    public Observacion(){}

    public Observacion(int supuesta, int real){
        this.cantSupuesta=supuesta;
        this.cantReal=real;
    }

    public int getCantSupuesta() {
        return cantSupuesta;
    }

    public void setCantSupuesta(int cantSupuesta) {
        this.cantSupuesta = cantSupuesta;
    }

    public int getCantReal() {
        return cantReal;
    }

    public void setCantReal(int cantReal) {
        this.cantReal = cantReal;
    }
}
