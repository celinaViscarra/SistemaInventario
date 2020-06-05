package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Motivo.class,
                parentColumns = "motivo_id",
                childColumns = "motivo_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = EquipoInformatico.class,
                parentColumns = "equipo_id",
                childColumns = "equipo_obsoleto_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = EquipoInformatico.class,
                parentColumns = "equipo_id",
                childColumns = "equipo_reemplazo_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Docente.class,
                parentColumns = "docentes_id",
                childColumns = "docentes_id",
                onDelete = CASCADE
        )
})

public class Sustituciones {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sustitucion_id")
    public int idSustituciones;

    @NonNull
    @ColumnInfo(name = "motivo_id")
    public int idMotivo;

    @NonNull
    @ColumnInfo(name = "equipo_obsoleto_id")
    public int idEquipoObsoleto;

    @NonNull
    @ColumnInfo(name = "equipo_reemplazo_id")
    public int idEquipoReemplazo;

    @NonNull
    @ColumnInfo(name = "docentes_id")
    public int idDocentes;

    @Ignore
    public Sustituciones(){}

    public Sustituciones(int idSustituciones, int idMotivo, int idEquipoObsoleto,
                         int idEquipoReemplazo, int idDocentes){
        this.idSustituciones=idSustituciones;
        this.idMotivo=idMotivo;
        this.idEquipoObsoleto=idEquipoObsoleto;
        this.idEquipoReemplazo=idEquipoReemplazo;
        this.idDocentes=idDocentes;
    }

}
