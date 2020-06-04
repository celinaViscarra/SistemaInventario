package com.grupo13.inventario.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AutorConDetalleAutor {
    @Embedded Autor autor;
    @Relation(
            parentColumn = "idAutor",
            entityColumn = "idAutor"
    )
    List<DetalleAutor> detalles;
}
