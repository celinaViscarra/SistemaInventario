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
    public String diaCod;
    @NonNull
    @ColumnInfo(name = "hora_id")
    public int idHora;
    @NonNull
    @ColumnInfo(name = "prestamo_id")
    public int idPrestamos;

    public  DetalleReserva(){}

    public DetalleReserva(String diaCod, int idHora, int idPrestamos){
        this.diaCod=diaCod;
        this.idHora=idHora;
        this.idPrestamos=idPrestamos;
    }
}
