package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity
public class Categorias {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoria_id")
    public int idCategoria;
    @NonNull
    @ColumnInfo(name = "categoria_nombre")
    public String nomCategoria;

    @Ignore
    public Categorias(){

    }

    public Categorias(int idCategoria, String nomCategoria){
        this.idCategoria=idCategoria;
        this.nomCategoria=nomCategoria;
    }
}
