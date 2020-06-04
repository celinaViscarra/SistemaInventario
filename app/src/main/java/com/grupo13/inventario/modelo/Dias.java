package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dias {
    @PrimaryKey
    @ColumnInfo(name ="dia_cod")
    String diaCod;

    @ColumnInfo(name = "dia_nombre")
    String diaNombre;

    public Dias(){}

    public Dias(String diaCod, String diaNombre){
        this.diaCod=diaCod;
        this.diaNombre=diaNombre;
    }

    public String getDiaCod() {
        return diaCod;
    }

    public void setDiaCod(String diaCod) {
        this.diaCod = diaCod;
    }

    public String getDiaNombre() {
        return diaNombre;
    }

    public void setDiaNombre(String diaNombre) {
        this.diaNombre = diaNombre;
    }
}
