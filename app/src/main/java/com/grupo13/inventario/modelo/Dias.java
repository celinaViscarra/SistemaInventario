package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dias {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name ="dia_cod")
    public String diaCod;
    @NonNull
    @ColumnInfo(name = "dia_nombre")
    public String diaNombre;

    public Dias(){}

    public Dias(String diaCod, String diaNombre){
        this.diaCod=diaCod;
        this.diaNombre=diaNombre;
    }
}
