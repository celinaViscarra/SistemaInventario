package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        primaryKeys = {"dia_id","dia_cod"},
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
    @ColumnInfo(name = "hora_id")
    int idHora;

    @ColumnInfo(name = "dia_cod")
    String diaCod;

    @Ignore
    public Horarios(){

    }

    public Horarios(int idHora, String diaCod) {
        this.diaCod = diaCod;
        this.idHora = idHora;
    }

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public String getDiaCod() {
        return diaCod;
    }

    public void setDiaCod(String diaCod) {
        this.diaCod = diaCod;
    }
}
