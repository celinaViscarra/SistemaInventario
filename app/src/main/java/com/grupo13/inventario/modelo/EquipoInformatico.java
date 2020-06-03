package com.grupo13.inventario.modelo;

import java.sql.Date;

public class EquipoInformatico {
    int idEquipo;
    String codEquipo;
    Date fechaAdquisicion;
    String estadoEquipo;

    public EquipoInformatico(){}

    public EquipoInformatico(int id, String cod, Date fecha, String estado){
        this.idEquipo=id;
        this.codEquipo=cod;
        this.fechaAdquisicion=fecha;
        this.estadoEquipo=estado;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getCodEquipo() {
        return codEquipo;
    }

    public void setCodEquipo(String codEquipo) {
        this.codEquipo = codEquipo;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getEstadoEquipo() {
        return estadoEquipo;
    }

    public void setEstadoEquipo(String estadoEquipo) {
        this.estadoEquipo = estadoEquipo;
    }
}
