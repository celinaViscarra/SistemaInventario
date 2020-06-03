package com.grupo13.inventario;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Idiomas {
    @PrimaryKey(autoGenerate = true) int idIdioma;
    String nombreIdioma;

    @Ignore
    public Idiomas(){}

    public Idiomas(int id, String nom){
        this.idIdioma = id;
        this.nombreIdioma = nom;
    }

    public int getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getNombreIdioma() {
        return nombreIdioma;
    }

    public void setNombreIdioma(String nombreIdioma) {
        this.nombreIdioma = nombreIdioma;
    }
}
