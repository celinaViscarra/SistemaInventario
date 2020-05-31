package com.grupo13.inventario;

import java.sql.Date;

public class TomaFisica {
    int idToma;
    Date fechaToma;

    public TomaFisica(){}

    public TomaFisica(int id, Date fecha){
        this.idToma=id;
        this.fechaToma=fecha;
    }

    public int getIdToma() {
        return idToma;
    }

    public void setIdToma(int idToma) {
        this.idToma = idToma;
    }

    public Date getFechaToma() {
        return fechaToma;
    }

    public void setFechaToma(Date fechaToma) {
        this.fechaToma = fechaToma;
    }
}
