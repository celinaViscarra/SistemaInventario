package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"escrito_id","idAutor"}, foreignKeys = {
        @ForeignKey(entity = Documento.class,
                    parentColumns = "escrito_id",
                    childColumns = "escrito_id",
                    onDelete = CASCADE),
        @ForeignKey(entity = Autor.class,
                    parentColumns = "idAutor",
                    childColumns = "idAutor",
                    onDelete = CASCADE)
})
public class DetalleAutor {
    @NonNull
    public int idAutor;
    @NonNull
    public int escrito_id;
    @NonNull
    public boolean esPrincipal;

    public DetalleAutor(int idAutor, int escrito_id, boolean esPrincipal) {
        this.idAutor = idAutor;
        this.escrito_id = escrito_id;
        this.esPrincipal = esPrincipal;
    }

    @Ignore
    public DetalleAutor(){

    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public int getEscrito_id() {
        return escrito_id;
    }

    public void setEscrito_id(int escrito_id) {
        this.escrito_id = escrito_id;
    }

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}
