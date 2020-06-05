package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"descargo_id", "equipo_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Descargos.class,
                        parentColumns = "descargo_id",
                        childColumns = "descargo_id",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = EquipoInformatico.class,
                        parentColumns = "equipo_id",
                        childColumns = "equipo_id",
                        onDelete = CASCADE
                )
})

public class DetalleDescargos {
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
