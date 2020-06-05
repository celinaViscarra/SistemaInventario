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
                entity = Marca.class,
                parentColumns = "marca_id",
                childColumns = "marca_id",
                onDelete = CASCADE
        )
})

public class CatalogoEquipo {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "catalogo_id")
    public String idCatalogo;
    @NonNull
    @ColumnInfo(name = "marca_id")
    public String idMarca;
    @NonNull
    @ColumnInfo(name = "modelo_equipo_generico")
    public String modeloEquipo;
    @NonNull
    public int memoria;
    @NonNull
    @ColumnInfo(name = "cantidad_equipo")
    public int cantEquipo;

    public CatalogoEquipo(@NonNull String idCatalogo, @NonNull String idMarca, @NonNull String modeloEquipo, int memoria, int cantEquipo) {
        this.idCatalogo = idCatalogo;
        this.idMarca = idMarca;
        this.modeloEquipo = modeloEquipo;
        this.memoria = memoria;
        this.cantEquipo = cantEquipo;
    }

    @Ignore
    public CatalogoEquipo(){}
}
