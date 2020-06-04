package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.DetalleReserva;

import java.util.List;

@Dao
public interface DetalleReservaDao {
    @Query("SELECT * FROM DetalleReserva")
    List<DetalleReserva> obtenerDetallesReserva();

    @Query("SELECT * FROM DetalleReserva WHERE hora_id  = :hora_id AND dia_cod = :dia_cod AND prestamo_id = :prestamo_id")
    DetalleReserva consultarDetalleReserva(int hora_id, String dia_cod, int prestamo_id);

    @Insert
    void insertarDetalleReserva(DetalleReserva detalleReserva);

    @Update
    void actualizarDetalleReserva(DetalleReserva detalleReserva);

    @Delete
    void eliminarDetalleReserva(DetalleReserva detalleReserva);
}
