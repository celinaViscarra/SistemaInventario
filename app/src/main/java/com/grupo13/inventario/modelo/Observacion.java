package com.grupo13.inventario.modelo;

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
    @ColumnInfo(name = "cant_supuesta")
    public int cantSupuesta;

    @ColumnInfo(name = "cant_real")
    public int cantReal;

    public String catologo_id;
    public int toma_id;

    @Ignore
    public Observacion(){}

    public Observacion(String catalogo, int toma, int supuesta, int real){
        this.catologo_id=catalogo;
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
        return catologo_id;
    }

    public void setCatologo_id(String catologo_id) {
        this.catologo_id = catologo_id;
    }

    public int getToma_id() {
        return toma_id;
    }

    public void setToma_id(int toma_id) {
        this.toma_id = toma_id;
    }
}
