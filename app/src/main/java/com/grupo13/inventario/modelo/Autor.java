package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Autor {
    @PrimaryKey(autoGenerate = true)
    public int idAutor;
    public String nomAutor;
    public String apeAutor;

    @Ignore
    public Autor(){}

    public Autor(int idAutor, String nomAutor, String apeAutor){
        this.idAutor = idAutor;
        this.nomAutor = nomAutor;
        this.apeAutor = apeAutor;
    }
    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNomAutor() {
        return nomAutor;
    }

    public void setNomAutor(String nomAutor) {
        this.nomAutor = nomAutor;
    }

    public String getApeAutor() {
        return apeAutor;
    }

    public void setApeAutor(String apeAutor) {
        this.apeAutor = apeAutor;
    }
}
