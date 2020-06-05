package com.grupo13.inventario.modelo;

import java.sql.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Ubicaciones.class,
                parentColumns = "ubicacion_origen_id",
                childColumns = "ubicacion_origen_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Ubicaciones.class,
                parentColumns = "ubicacion_destino_id",
                childColumns = "ubicacion_destino_id",
                onDelete = CASCADE
        )

})

public class Descargos {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "descargo_id")
    public int idDescargos;

    @NonNull
    @ColumnInfo(name = "descargo_fecha")
    public Date fechaDescargos;

    @NonNull
    public int ubicacion_origen_id;
    @NonNull
    public int ubicacion_destino_id;

    public Descargos(int idDescargos, int origen, int destino, Date fechaDescargos){
        this.idDescargos=idDescargos;
        this.ubicacion_origen_id=origen;
        this.ubicacion_destino_id=destino;
        this.fechaDescargos=fechaDescargos;
    }

    public Descargos(){

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
