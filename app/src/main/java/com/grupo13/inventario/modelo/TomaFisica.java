package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Ubicaciones.class,
                parentColumns = "ubicacion_id",
                childColumns = "ubicacion_id",
                onDelete = CASCADE
        )
})

public class TomaFisica {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "toma_id")
    public int idToma;

    @NonNull
    @ColumnInfo(name = "ubicacion_id")
    public int idUbicacion;

    @NonNull
    @ColumnInfo(name = "toma_fecha")
    public Date fechaToma;

    
    public TomaFisica(){}

    public TomaFisica(int id, int ubicacion, Date fecha){
        this.idToma=id;
        this.idUbicacion=ubicacion;
        this.fechaToma=fecha;
    }

    public int getIdToma() {
        return idToma;
    }

    public void setIdToma(int idToma) {
        this.idToma = idToma;
    }

    public Date getFechaToma() {
        return fechaToma;
    }

    public void setFechaToma(Date fechaToma) {
        this.fechaToma = fechaToma;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }
}
