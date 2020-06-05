package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        primaryKeys = {"hora_id","dia_cod"},
        foreignKeys = {
                @ForeignKey(
                        entity = Dias.class,
                        parentColumns = "dia_cod",
                        childColumns = "dia_cod",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = HoraClase.class,
                        parentColumns = "hora_id",
                        childColumns = "hora_id",
                        onDelete = CASCADE
                )
        }
)
public class Horarios {
    @NonNull
    @ColumnInfo(name = "hora_id")
    int idHora;

    @NonNull
    @ColumnInfo(name = "dia_cod")
    String diaCod;

    public Horarios(){}

    public Horarios(int idHora, String diaCod) {
        this.diaCod = diaCod;
        this.idHora = idHora;
    }
}
