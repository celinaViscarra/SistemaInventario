package com.grupo13.inventario.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DocumentoConParticipacionesDocentes {
    @Embedded Documento documento;
    @Relation(
            parentColumn = "escrito_id",
            entityColumn = "escrito_id"
    )
    List<ParticipacionDocente> participaciones;
}
