package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        primaryKeys = {
                "dia_cod",
                "hora_id",
                "prestamo_id"
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Horarios.class,
                        parentColumns = {
                                "hora_id",
                                "dia_cod"
                        },
                        childColumns = {
                                "hora_id",
                                "dia_cod"
                        },
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = MovimientoInventario.class,
                        parentColumns = "prestamo_id",
                        childColumns = "prestamo_id",
                        onDelete = CASCADE
                )
        }
)
public class DetalleReserva {
    @NonNull
    @ColumnInfo(name = "dia_cod")
    String diaCod;
    @NonNull
    @ColumnInfo(name = "hora_id")
    int idHora;
    @NonNull
    @ColumnInfo(name = "prestamo_id")
    int idPrestamos;

    public DetalleReserva(String diaCod, int idHora, int idPrestamos){
        this.diaCod=diaCod;
        this.idHora=idHora;
        this.idPrestamos=idPrestamos;
    }

    public String getDiaCod() {
        return diaCod;
    }

    public void setDiaCod(String diaCod) {
        this.diaCod = diaCod;
    }

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public int getIdPrestamos() {
        return idPrestamos;
    }

    public void setIdPrestamos(int idPrestamos) {
        this.idPrestamos = idPrestamos;
    }
}
