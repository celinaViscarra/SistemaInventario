package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"dia_cod","hora_id","prestamo_id"})
public class DetalleReserva {
    @ColumnInfo(name = "dia_cod")
    String diaCod;
    @ColumnInfo(name = "hora_id")
    int idHora;
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
