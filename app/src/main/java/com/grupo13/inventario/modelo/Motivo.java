package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Motivo {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "motivo_id")
    public int idMotivo;

    @NonNull
    @ColumnInfo(name = "motivo_nombre")
    public String nomMotivo;

    @Ignore
    public Motivo(){}

    public Motivo(int id, String nom){
        this.idMotivo=id;
        this.nomMotivo=nom;
    }

    public int getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(int idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getNomMotivo() {
        return nomMotivo;
    }

    public void setNomMotivo(String nomMotivo) {
        this.nomMotivo = nomMotivo;
    }
}
