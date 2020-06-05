package com.grupo13.inventario.modelo;

import java.sql.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = TipoProducto.class,
                parentColumns = "tipo_producto_id",
                childColumns = "tipo_producto_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Ubicaciones.class,
                parentColumns = "ubicacion_id",
                childColumns = "ubicacion_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = CatalogoEquipo.class,
                parentColumns = "catalogo_id",
                childColumns = "catalogo_id",
                onDelete = CASCADE
        )

})

public class EquipoInformatico {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "equipo_id")
    public int idEquipo;

    @NonNull
    @ColumnInfo(name = "codigo_equipo")
    public String codEquipo;

    @NonNull
    @ColumnInfo(name = "fecha_adquisicion")
    public Date fechaAdquisicion;

    @NonNull
    @ColumnInfo(name = "estado_equipo")
    public String estadoEquipo;

    @NonNull
    public int tipo_producto_id;
    @NonNull
    public int ubicacion_id;
    @NonNull
    public int catalogo_id;


    public EquipoInformatico(){}

    public EquipoInformatico(int idEquipo, @NonNull String codEquipo, @NonNull Date fechaAdquisicion, @NonNull String estadoEquipo, int tipo_producto_id, int ubicacion_id, int catalogo_id) {
        this.idEquipo = idEquipo;
        this.codEquipo = codEquipo;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estadoEquipo = estadoEquipo;
        this.tipo_producto_id = tipo_producto_id;
        this.ubicacion_id = ubicacion_id;
        this.catalogo_id = catalogo_id;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getCodEquipo() {
        return codEquipo;
    }

    public void setCodEquipo(String codEquipo) {
        this.codEquipo = codEquipo;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getEstadoEquipo() {
        return estadoEquipo;
    }

    public void setEstadoEquipo(String estadoEquipo) {
        this.estadoEquipo = estadoEquipo;
    }

    public int getTipo_producto_id() {
        return tipo_producto_id;
    }

    public void setTipo_producto_id(int tipo_producto_id) {
        this.tipo_producto_id = tipo_producto_id;
    }

    public int getUbicacion_id() {
        return ubicacion_id;
    }

    public void setUbicacion_id(int ubicacion_id) {
        this.ubicacion_id = ubicacion_id;
    }
}
