package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
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
                        childColumns = "docentes_id",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = EquipoInformatico.class,
                        parentColumns = "equipo_id",
                        childColumns = "equipo_id",
                        onDelete = CASCADE
                )
        }
)
public class MovimientoInventario {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "prestamo_id")
    public int idPresatamo;

    @NonNull
    @ColumnInfo(name = "prestamo_fecha_ini")
    public Date prestamoFechaInicio;

    @ColumnInfo(name = "prestamo_fecha_fin")
    public Date prestamoFechaFin;

    @NonNull
    @ColumnInfo(name = "prestamo_permanente")
    public Boolean prestamoPermanente;

    @NonNull
    @ColumnInfo(name = "prestamo_activo")
    public Boolean prestamoActivo;

    public String descripcion;

    public int docentes_id;

    public int tipo_movimiento_id;
    public int equipo_id;

    public MovimientoInventario(){}

    public MovimientoInventario(int idPresatamo, Date prestamoFechaInicio, Date prestamoFechaFin,
                                boolean prestamoPermanente, boolean prestamoActivo, String descripcion,
                                int docentes_id, int tipo_movimiento_id, int equipo_id){
        this.idPresatamo=idPresatamo;
        this.prestamoFechaInicio=prestamoFechaInicio;
        this.prestamoFechaFin=prestamoFechaFin;
        this.prestamoPermanente=prestamoPermanente;
        this.prestamoActivo=prestamoActivo;
        this.descripcion=descripcion;
        this.docentes_id=docentes_id;
        this.tipo_movimiento_id=tipo_movimiento_id;
        this.equipo_id=equipo_id;
    }
}
