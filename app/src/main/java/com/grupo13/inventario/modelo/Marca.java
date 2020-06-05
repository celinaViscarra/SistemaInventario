package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Marca {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "marca_id")
    public int idMarca;

    @NonNull
    @ColumnInfo(name = "marca_nombre")
    public String nomMarca;

    public Marca(int id, String nom){
        this.idMarca=id;
        this.nomMarca=nom;
    }

    public Marca(){

    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNomMarca() {
        return nomMarca;
    }

    public void setNomMarca(String nomMarca) {
        this.nomMarca = nomMarca;
    }
}
