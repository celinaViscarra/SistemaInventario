package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class TipoMovimiento {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tipo_movimiento_id")
    int idTipoMovimiento;

    @ColumnInfo(name = "tipo_movimiento_nombre")
    String nombreTipoMoviento;

    @Ignore
    public TipoMovimiento(){}

    public TipoMovimiento(int id, String nom){
        this.idTipoMovimiento=id;
        this.nombreTipoMoviento=nom;
    }

    public int getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(int idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public String getNombreTipoMoviento() {
        return nombreTipoMoviento;
    }

    public void setNombreTipoMoviento(String nombreTipoMoviento) {
        this.nombreTipoMoviento = nombreTipoMoviento;
    }

}
