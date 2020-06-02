package com.grupo13.inventario;

public class DetalleDescargos {
    int idDescargo, idEquipo;

    public DetalleDescargos(int id1, int id2){
        this.idDescargo=id1;
        this.idEquipo=id2;
    }

    public int getIdDescargo() {
        return idDescargo;
    }

    public void setIdDescargo(int idDescargo) {
        this.idDescargo = idDescargo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
}
