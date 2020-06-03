package com.grupo13.inventario.modelo;

import java.sql.Date;

public class Descargos {
    int idDescargos;
    Date fechaDescargos;

    public Descargos(int idDescargos, Date fechaDescargos){
        this.idDescargos=idDescargos;
        this.fechaDescargos=fechaDescargos;
    }

    public int getIdDescargos() {
        return idDescargos;
    }

    public void setIdDescargos(int idDescargos) {
        this.idDescargos = idDescargos;
    }

    public Date getFechaDescargos() {
        return fechaDescargos;
    }

    public void setFechaDescargos(Date fechaDescargos) {
        this.fechaDescargos = fechaDescargos;
    }
}
