package com.grupo13.inventario.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class IdiomaConDocumentos {
    @Embedded public Idiomas idioma;
    @Relation(
            parentColumn = "idioma_id",
            entityColumn = "idioma_id"
    )
    public List<Documento> documentos;
}
