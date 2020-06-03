package com.grupo13.inventario.modelo;

public class DetalleAutor {
    boolean esPrincipal;

    public DetalleAutor(){}

    public  DetalleAutor(boolean esPrincipal){
        this.esPrincipal=esPrincipal;
    }

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}
