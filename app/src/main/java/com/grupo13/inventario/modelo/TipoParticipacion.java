package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity
public class TipoParticipacion {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "participacion_id")
    public int idParticipacion;
    @NonNull
    @ColumnInfo(name = "participacion_nombre")
    public String nomParticipacion;


    public  TipoParticipacion(){}

    public  TipoParticipacion(int id, String nom){
        this.idParticipacion=id;
        this.nomParticipacion=nom;
    }
}
