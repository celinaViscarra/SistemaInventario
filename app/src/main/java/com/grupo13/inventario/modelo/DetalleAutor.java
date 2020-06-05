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

    public DetalleAutor(){}
}
