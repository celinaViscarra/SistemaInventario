package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity
public class Categorias {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoria_id")
    int idCategoria;
    @ColumnInfo(name = "categoria_nombre")
    String nomCategoria;

    @Ignore
    public Categorias(){

    }

    public Categorias(int idCategoria, String nomCategoria){
        this.idCategoria=idCategoria;
        this.nomCategoria=nomCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }
}
