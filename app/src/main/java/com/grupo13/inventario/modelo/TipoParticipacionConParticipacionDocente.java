package com.grupo13.inventario.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TipoParticipacionConParticipacionDocente {
    @Embedded TipoParticipacion tipoParticipacion;

    @Relation(
            parentColumn = "participacion_id",
            entityColumn = "participacion_id"
    )
    List<ParticipacionDocente> participaciones;
}
