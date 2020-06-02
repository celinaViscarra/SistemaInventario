package com.grupo13.inventario;

public class DetalleDescargos {
    int idDescargo, idEquipo;

    public DetalleDescargos(int idDescargo, int idEquipo){
        this.idDescargo=idDescargo;
        this.idEquipo=idEquipo;
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
