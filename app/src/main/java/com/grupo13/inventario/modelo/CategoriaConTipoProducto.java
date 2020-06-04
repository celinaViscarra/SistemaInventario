package com.grupo13.inventario.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoriaConTipoProducto {
    @Embedded Categorias categorias;

    @Relation(
            parentColumn = "categoria_id",
            entityColumn = "categoria_id"
    )
    List<TipoProducto> tipos;
}
