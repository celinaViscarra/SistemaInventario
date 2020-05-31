package com.grupo13.inventario;

public class DetalleAutor {
    boolean esPrincipal;

    public DetalleAutor(){}

    public  DetalleAutor(boolean ep){
        this.esPrincipal=ep;
    }

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}
