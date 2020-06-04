package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        primaryKeys = {"escrito_id","docentes_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = TipoParticipacion.class,
                        parentColumns = "participacion_id",
                        childColumns = "participacion_id",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Docente.class,
                        parentColumns = "docentes_id",
                        childColumns = "docentes_id",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Documento.class,
                        parentColumns = "escrito_id",
                        childColumns = "escrito_id",
                        onDelete = CASCADE
                )
        }
)
public class ParticipacionDocente {
    @ColumnInfo(name = "escrito_id")
    public int idDocentes;
    @ColumnInfo(name = "docentes_id")
    public int idEscritos;
    @ColumnInfo(name = "participacion_id")
    public int idParticipacion;

    @Ignore
    public ParticipacionDocente(){

    }

    public ParticipacionDocente(int idDocentes, int idEscritos, int idParticipacion){
        this.idDocentes=idDocentes;
        this.idEscritos=idEscritos;
        this.idParticipacion=idParticipacion;
    }

    public int getIdDocentes() {
        return idDocentes;
    }

    public void setIdDocentes(int idDocentes) {
        this.idDocentes = idDocentes;
    }

    public int getIdEscritos() {
        return idEscritos;
    }

    public void setIdEscritos(int idEscritos) {
        this.idEscritos = idEscritos;
    }

    public int getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(int idParticipacion) {
        this.idParticipacion = idParticipacion;
    }
}
