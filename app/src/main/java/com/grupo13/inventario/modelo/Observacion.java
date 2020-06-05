package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"catalogo_id", "toma_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = CatalogoEquipo.class,
                        parentColumns = "catalogo_id",
                        childColumns = "catalogo_id",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = TomaFisica.class,
                        parentColumns = "toma_id",
                        childColumns = "toma_id",
                        onDelete = CASCADE
                )
})

public class Observacion {
    @NonNull
    @ColumnInfo(name = "cant_supuesta")
    public int cantSupuesta;

    @NonNull
    @ColumnInfo(name = "cant_real")
    public int cantReal;

    @NonNull
    public String catalogo_id;

    @NonNull
    public int toma_id;


    public Observacion(){}

    public Observacion(String catalogo, int toma, int supuesta, int real){
        this.catalogo_id=catalogo;
        this.toma_id=toma;
        this.cantSupuesta=supuesta;
        this.cantReal=real;
    }

    public int getCantSupuesta() {
        return cantSupuesta;
    }

    public void setCantSupuesta(int cantSupuesta) {
        this.cantSupuesta = cantSupuesta;
    }

    public int getCantReal() {
        return cantReal;
    }

    public void setCantReal(int cantReal) {
        this.cantReal = cantReal;
    }

    public String getCatologo_id() {
        return catalogo_id;
    }

    public void setCatologo_id(String catologo_id) {
        this.catalogo_id = catologo_id;
    }

    public int getToma_id() {
        return toma_id;
    }

    public void setToma_id(int toma_id) {
        this.toma_id = toma_id;
    }
}
