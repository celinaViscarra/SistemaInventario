package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Autor {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int idAutor;
    @NonNull
    public String nomAutor;
    @NonNull
    public String apeAutor;

    public Autor(){}

    public Autor(int idAutor, String nomAutor, String apeAutor){
        this.idAutor = idAutor;
        this.nomAutor = nomAutor;
        this.apeAutor = apeAutor;
    }
}
