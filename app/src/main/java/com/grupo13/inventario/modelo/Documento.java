package com.grupo13.inventario.modelo;

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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "escrito_id")
    public int idEscrito;

    public int idioma_id;
    public int tipo_producto_id;

    public String isbn;
    public String edicion;
    public String editorial;
    public String titulo;

    @Ignore
    public  Documento(){}

    public Documento(int idEscrito, int idioma_id, int tipo_producto_id, String isbn, String edicion, String editorial, String titulo) {
        this.idEscrito = idEscrito;
        this.idioma_id = idioma_id;
        this.tipo_producto_id = tipo_producto_id;
        this.isbn = isbn;
        this.edicion = edicion;
        this.editorial = editorial;
        this.titulo = titulo;
    }

    public int getIdEscrito() {
        return idEscrito;
    }

    public void setIdEscrito(int idEscrito) {
        this.idEscrito = idEscrito;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdioma_id() {
        return idioma_id;
    }

    public void setIdioma_id(int idioma_id) {
        this.idioma_id = idioma_id;
    }
}
