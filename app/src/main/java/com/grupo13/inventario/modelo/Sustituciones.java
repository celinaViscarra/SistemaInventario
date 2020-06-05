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
                parentColumns = "equipo_obsoleto_id",
                childColumns = "equipo_obsoleto_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = EquipoInformatico.class,
                parentColumns = "equipo_reemplazo_id",
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
    
    public int getIdSustituciones() {
        return idSustituciones;
    }

    public void setIdSustituciones(int idSustituciones) {
        this.idSustituciones = idSustituciones;
    }

    public int getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(int idMotivo) {
        this.idMotivo = idMotivo;
    }

    public int getIdEquipoObsoleto() {
        return idEquipoObsoleto;
    }

    public void setIdEquipoObsoleto(int idEquipoObsoleto) {
        this.idEquipoObsoleto = idEquipoObsoleto;
    }

    public int getIdEquipoReemplazo() {
        return idEquipoReemplazo;
    }

    public void setIdEquipoReemplazo(int idEquipoReemplazo) {
        this.idEquipoReemplazo = idEquipoReemplazo;
    }

    public int getIdDocentes() {
        return idDocentes;
    }

    public void setIdDocentes(int idDocentes) {
        this.idDocentes = idDocentes;
    }
}
