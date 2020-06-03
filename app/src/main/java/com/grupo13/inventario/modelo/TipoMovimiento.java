package com.grupo13.inventario.modelo;

public class TipoMovimiento {
    int idTipoMovimiento;
    String nombreTipoMoviento;

    public TipoMovimiento(){}

    public TipoMovimiento(int id, String nom){
        this.idTipoMovimiento=id;
        this.nombreTipoMoviento=nom;
    }

    public int getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(int idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public String getNombreTipoMoviento() {
        return nombreTipoMoviento;
    }

    public void setNombreTipoMoviento(String nombreTipoMoviento) {
        this.nombreTipoMoviento = nombreTipoMoviento;
    }
}
