package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Docente {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "docentes_id")
    public int idDocente;
    @ColumnInfo(name = "docentes_nombre")
    public String nomDocente;

    @Ignore
    public Docente(){}

    public Docente(int idDocente, String nomDocente){
        this.idDocente=idDocente;
        this.nomDocente=nomDocente;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getNomDocente() {
        return nomDocente;
    }

    public void setNomDocente(String nomDocente) {
        this.nomDocente = nomDocente;
    }
}
