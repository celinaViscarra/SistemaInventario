package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ubicaciones {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ubicacion_id")
    int idUbicacion;
    @ColumnInfo(name = "ubicacion_nombre")
    String nomUbicacion;

    @Ignore
    public Ubicaciones(){}

    public Ubicaciones(int id, String nom){
        this.idUbicacion=id;
        this.nomUbicacion=nom;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getNomUbicacion() {
        return nomUbicacion;
    }

    public void setNomUbicacion(String nomUbicacion) {
        this.nomUbicacion = nomUbicacion;
    }
}
