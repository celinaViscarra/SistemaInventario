package com.grupo13.inventario.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DocenteConMovimientosInventario {
    @Embedded Docente docente;
    @Relation(
            parentColumn = "docentes_id",
            entityColumn = "docentes_id"
    )
    List<MovimientoInventario> momvimientos;
}
