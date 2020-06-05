package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

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
    @NonNull
    @ColumnInfo(name = "descargo_id")
    public int idDescargo;

    @NonNull
    @ColumnInfo(name = "equipo_id")
    public int idEquipo;

    public DetalleDescargos(){}

    public DetalleDescargos(int idDescargo, int idEquipo){
        this.idDescargo=idDescargo;
        this.idEquipo=idEquipo;
    }
}
