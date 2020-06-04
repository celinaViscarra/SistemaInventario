package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity
public class TomaFisica {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "toma_id")
    public int idToma;

    @ColumnInfo(name = "ubicacion_id")
    public int idUbicacion;

    @ColumnInfo(name = "toma_fecha")
    public Date fechaToma;

    @Ignore
    public TomaFisica(){}

    public TomaFisica(int id, int ubicacion, Date fecha){
        this.idToma=id;
        this.idUbicacion=ubicacion;
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

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }
}
