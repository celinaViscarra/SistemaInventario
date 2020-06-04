package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class TipoProducto {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="tipo_producto_id")
    int idTipoProducto;
    @ColumnInfo(name = "nombre_tipo_producto")
    String nomTipoProducto;

    @Ignore
    public TipoProducto(){}

    public TipoProducto(int id, String nom){
        this.idTipoProducto=id;
        this.nomTipoProducto=nom;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getNomTipoProducto() {
        return nomTipoProducto;
    }

    public void setNomTipoProducto(String nomTipoProducto) {
        this.nomTipoProducto = nomTipoProducto;
    }
}
