package com.grupo13.inventario.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TipoProductoConDocumentos {
    @Embedded TipoProducto tipoProducto;
    @Relation(
            parentColumn = "tipo_producto_id",
            entityColumn = "tipo_producto_id"
    )
    List<Documento> documentos;
}
