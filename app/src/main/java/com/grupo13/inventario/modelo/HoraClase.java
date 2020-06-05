package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity
public class HoraClase {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hora_id")
    int idHora;

    @NonNull
    @ColumnInfo(name = "hora_inicio")
    Time horaInicio;

    @NonNull
    @ColumnInfo(name = "hora_fin")
    Time horaFin;

    public HoraClase(){}

    public HoraClase(int idHora, Time horaInicio, Time horaFin){
        this.horaFin=horaFin;
        this.horaInicio=horaInicio;
        this.idHora=idHora;
    }
}
