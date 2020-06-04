package com.grupo13.inventario.modelo;

import java.sql.Date;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Descargos {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "descargo_id")
    public int idDescargos;

    @ColumnInfo(name = "descargo_fecha")
    public Date fechaDescargos;

    public int ubicacion_origen_id;
    public int ubicacion_destino_id;

    public Descargos(int idDescargos, int origen, int destino, Date fechaDescargos){
        this.idDescargos=idDescargos;
        this.ubicacion_origen_id=origen;
        this.ubicacion_destino_id=destino;
        this.fechaDescargos=fechaDescargos;
    }

    public int getUbicacion_origen_id() {
        return ubicacion_origen_id;
    }

    public void setUbicacion_origen_id(int ubicacion_origen_id) {
        this.ubicacion_origen_id = ubicacion_origen_id;
    }

    public int getUbicacion_destino_id() {
        return ubicacion_destino_id;
    }

    public void setUbicacion_destino_id(int ubicacion_destino_id) {
        this.ubicacion_destino_id = ubicacion_destino_id;
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
