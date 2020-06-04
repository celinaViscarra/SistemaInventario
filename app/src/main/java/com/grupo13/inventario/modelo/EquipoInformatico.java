package com.grupo13.inventario.modelo;

import java.sql.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class EquipoInformatico {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "equipo_id")
    public int idEquipo;

    @ColumnInfo(name = "codigo_equipo")
    public String codEquipo;

    @ColumnInfo(name = "fecha_adquisicion")
    public Date fechaAdquisicion;

    @ColumnInfo(name = "estado_equipo")
    public String estadoEquipo;

    public int tipo_producto_id;
    public int ubicacion_id;

    @Ignore
    public EquipoInformatico(){}

    public EquipoInformatico(int id, int tipoProducto, int ubicacion, String cod, Date fecha, String estado){
        this.idEquipo=id;
        this.tipo_producto_id=tipoProducto;
        this.ubicacion_id=ubicacion;
        this.codEquipo=cod;
        this.fechaAdquisicion=fecha;
        this.estadoEquipo=estado;
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
