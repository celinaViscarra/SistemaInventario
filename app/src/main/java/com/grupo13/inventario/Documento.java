package com.grupo13.inventario;

public class Documento {
    int idEscrito;
    String isbn, edicion, editorial, titulo;

    public  Documento(){}

    public Documento(int id, String i, String e, String ed, String t){
        this.idEscrito = id;
        this.isbn = i;
        this.edicion = e;
        this.editorial = ed;
        this.titulo = t;
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
