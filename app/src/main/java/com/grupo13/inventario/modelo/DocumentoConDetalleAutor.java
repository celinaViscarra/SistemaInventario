package com.grupo13.inventario.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DocumentoConDetalleAutor {
    @Embedded Documento documento;
    @Relation(
            parentColumn = "escrito_id",
            entityColumn = "escrito_id"
    )
    List<DetalleAutor> detalles;
}
