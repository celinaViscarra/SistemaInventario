package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Documento {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "escrito_id")
    private int idEscrito;

    private String isbn;
    private String edicion;
    private String editorial;
    private String titulo;

    @Ignore
    public  Documento(){}

    public Documento(int idEscrito, String isbn, String edicion, String editorial, String titulo){
        this.idEscrito = idEscrito;
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
}
