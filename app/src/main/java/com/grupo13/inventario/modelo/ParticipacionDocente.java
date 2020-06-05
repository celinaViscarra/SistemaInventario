package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
    @NonNull
    @ColumnInfo(name = "docentes_id")
    public int idDocentes;
    @NonNull
    @ColumnInfo(name = "escrito_id")
    public int idEscritos;
    @NonNull
    @ColumnInfo(name = "participacion_id")
    public int idParticipacion;

    public ParticipacionDocente() {}

    public ParticipacionDocente(int idDocentes, int idEscritos, int idParticipacion) {
        this.idDocentes = idDocentes;
        this.idEscritos = idEscritos;
        this.idParticipacion = idParticipacion;
    }
}
