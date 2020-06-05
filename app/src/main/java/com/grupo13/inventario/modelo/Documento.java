package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Idiomas.class,
                parentColumns = "idioma_id",
                childColumns = "idioma_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = TipoProducto.class,
                parentColumns = "tipo_producto_id",
                childColumns = "tipo_producto_id",
                onDelete = CASCADE
        )

})
public class Documento {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "escrito_id")
    public int idEscrito;
    @NonNull
    public int idioma_id;
    @NonNull
    public int tipo_producto_id;
    @NonNull
    public String isbn;
    @NonNull
    public String edicion;
    @NonNull
    public String editorial;
    @NonNull
    public String titulo;

    public  Documento(){}

    public Documento(int idEscrito, int idioma_id, int tipo_producto_id, String isbn, String edicion,
                     String editorial, String titulo) {
        this.idEscrito = idEscrito;
        this.idioma_id = idioma_id;
        this.tipo_producto_id = tipo_producto_id;
        this.isbn = isbn;
        this.edicion = edicion;
        this.editorial = editorial;
        this.titulo = titulo;
    }
}
