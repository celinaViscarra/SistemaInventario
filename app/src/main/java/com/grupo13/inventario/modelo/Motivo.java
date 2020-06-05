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

    public Motivo(){}

    public Motivo(int id, String nom){
        this.idMotivo=id;
        this.nomMotivo=nom;
    }
}
