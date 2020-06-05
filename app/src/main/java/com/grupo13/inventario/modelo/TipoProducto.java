package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys =
    @ForeignKey(
        entity = Categorias.class,
        parentColumns = "categoria_id",
        childColumns = "categoria_id",
        onDelete = CASCADE
    )
)
public class TipoProducto {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="tipo_producto_id")
    int idTipoProducto;

    @NonNull
    @ColumnInfo(name = "nombre_tipo_producto")
    String nomTipoProducto;

    int categoria_id;

    @Ignore
    public TipoProducto(){}

    public TipoProducto(int idTipoProducto, @NonNull String nomTipoProducto, int categoria_id) {
        this.idTipoProducto = idTipoProducto;
        this.nomTipoProducto = nomTipoProducto;
        this.categoria_id = categoria_id;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getNomTipoProducto() {
        return nomTipoProducto;
    }

    public void setNomTipoProducto(String nomTipoProducto) {
        this.nomTipoProducto = nomTipoProducto;
    }
}
