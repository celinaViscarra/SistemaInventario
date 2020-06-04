package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DetalleDescargos {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "descargo_id")
    public int idDescargo;

    @ColumnInfo(name = "equipo_id")
    public int idEquipo;

    public DetalleDescargos(int idDescargo, int idEquipo){
        this.idDescargo=idDescargo;
        this.idEquipo=idEquipo;
    }

    public int getIdDescargo() {
        return idDescargo;
    }

    public void setIdDescargo(int idDescargo) {
        this.idDescargo = idDescargo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
}
