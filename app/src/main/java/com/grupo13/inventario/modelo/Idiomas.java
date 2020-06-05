package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Idiomas {
    //En casos que el nombre del atributo no coincida con el de la clase
    //Se puede usar @ColumnInfo
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idioma_id")
    int idIdioma;

    @NonNull
    @ColumnInfo(name = "idioma_nombre")
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
