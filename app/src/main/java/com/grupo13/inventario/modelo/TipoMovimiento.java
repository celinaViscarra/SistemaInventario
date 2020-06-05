package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class TipoMovimiento {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tipo_movimiento_id")
    public int idTipoMovimiento;

    @NonNull
    @ColumnInfo(name = "tipo_movimiento_nombre")
    public String nombreTipoMoviento;

    public TipoMovimiento(){}

    public TipoMovimiento(int id, String nom){
        this.idTipoMovimiento=id;
        this.nombreTipoMoviento=nom;
    }
}
