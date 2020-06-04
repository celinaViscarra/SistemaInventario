package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = TipoMovimiento.class,
                        parentColumns = "tipo_movimiento_id",
                        childColumns = "tipo_movimiento_id",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Docente.class,
                        parentColumns = "docentes_id",
                        childColumns = "docented_id",
                        onDelete = CASCADE
                )
        }
)
public class MovimientoInventario {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "prestamo_id")
    int idPresatamo;

    @ColumnInfo(name = "prestamo_fecha_ini")
    Date prestamoFechaInicio;

    @ColumnInfo(name = "prestamo_fecha_fin")
    Date prestamoFechaFin;

    @ColumnInfo(name = "prestamo_permanente")
    Boolean prestamoPermanente;

    @ColumnInfo(name = "prestamo_activo")
    Boolean prestamoActivo;

    String descripcion;

    int docentes_id;

    int tipo_movimiento_id;
    int equipo_id;

    @Ignore
    public MovimientoInventario(){

    }

    public MovimientoInventario(int idPresatamo, Date prestamoFechaInicio, Date prestamoFechaFin, Boolean prestamoPermanente, Boolean prestamoActivo, String descripcion) {
        this.idPresatamo = idPresatamo;
        this.prestamoFechaInicio = prestamoFechaInicio;
        this.prestamoFechaFin = prestamoFechaFin;
        this.prestamoPermanente = prestamoPermanente;
        this.prestamoActivo = prestamoActivo;
        this.descripcion = descripcion;
    }

    public int getIdPresatamo() {
        return idPresatamo;
    }

    public void setIdPresatamo(int idPresatamo) {
        this.idPresatamo = idPresatamo;
    }

    public Date getPrestamoFechaInicio() {
        return prestamoFechaInicio;
    }

    public void setPrestamoFechaInicio(Date prestamoFechaInicio) {
        this.prestamoFechaInicio = prestamoFechaInicio;
    }

    public Date getPrestamoFechaFin() {
        return prestamoFechaFin;
    }

    public void setPrestamoFechaFin(Date prestamoFechaFin) {
        this.prestamoFechaFin = prestamoFechaFin;
    }

    public Boolean getPrestamoPermanente() {
        return prestamoPermanente;
    }

    public void setPrestamoPermanente(Boolean prestamoPermanente) {
        this.prestamoPermanente = prestamoPermanente;
    }

    public Boolean getPrestamoActivo() {
        return prestamoActivo;
    }

    public void setPrestamoActivo(Boolean prestamoActivo) {
        this.prestamoActivo = prestamoActivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
