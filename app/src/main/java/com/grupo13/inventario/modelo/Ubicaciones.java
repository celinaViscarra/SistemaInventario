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
    public int idUbicacion;

    @NonNull
    @ColumnInfo(name = "ubicacion_nombre")
    public String nomUbicacion;

    public Ubicaciones(){}

    public Ubicaciones(int id, String nom){
        this.idUbicacion=id;
        this.nomUbicacion=nom;
    }

}