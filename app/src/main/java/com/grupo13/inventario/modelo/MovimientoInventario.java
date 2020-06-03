package com.grupo13.inventario.modelo;

import java.sql.Date;

public class MovimientoInventario {
    int idPresatamo;
    Date prestamoFechaInicio, prestamoFechaFin;
    Boolean prestamoPermanente, prestamoActivo;

    public MovimientoInventario(){}

    public MovimientoInventario(int id, Date inicio, Date fin, boolean prestamoPermanente, boolean prestamoActivo){
        this.idPresatamo=id;
        this.prestamoFechaInicio=inicio;
        this.prestamoFechaFin=fin;
        this.prestamoPermanente=prestamoPermanente;
        this.prestamoActivo=prestamoActivo;
    }

    public int getIdPresatamo() {
        return idPresatamo;
    }

    public void setIdPresatamo(int idPresatamo) {
        this.idPresatamo = idPresatamo;
    }

    public Date getPrestamoFechaInicio() {
        return prestamoFechaInicio;
    }

    public void setPrestamoFechaInicio(Date prestamoFechaInicio) {
        this.prestamoFechaInicio = prestamoFechaInicio;
    }

    public Date getPrestamoFechaFin() {
        return prestamoFechaFin;
    }

    public void setPrestamoFechaFin(Date prestamoFechaFin) {
        this.prestamoFechaFin = prestamoFechaFin;
    }

    public Boolean getPrestamoPermanente() {
        return prestamoPermanente;
    }

    public void setPrestamoPermanente(Boolean prestamoPermanente) {
        this.prestamoPermanente = prestamoPermanente;
    }

    public Boolean getPrestamoActivo() {
        return prestamoActivo;
    }

    public void setPrestamoActivo(Boolean prestamoActivo) {
        this.prestamoActivo = prestamoActivo;
    }
}
