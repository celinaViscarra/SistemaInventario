package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ubicaciones {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ubicacion_id")
    int idUbicacion;

    @NonNull
    @ColumnInfo(name = "ubicacion_nombre")
    String nomUbicacion;


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
