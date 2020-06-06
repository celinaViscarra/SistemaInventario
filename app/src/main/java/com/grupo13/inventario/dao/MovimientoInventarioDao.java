package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.MovimientoInventario;

import java.util.List;

@Dao
public interface MovimientoInventarioDao {
    @Query("SELECT * FROM MovimientoInventario")
    List<MovimientoInventario> obtenerMovimientosInventario();

    @Query("SELECT * FROM MovimientoInventario WHERE prestamo_id = :prestamo_id")
    MovimientoInventario consultarMovimientoInventario(int prestamo_id);

    @Insert
    long insertarMovimientoInventario(MovimientoInventario movimientoInventario);

    @Update
    void actualizarMovimientoInventario(MovimientoInventario movimientoInventario);

    @Delete
    void eliminarMovimientoInventario(MovimientoInventario movimientoInventario);
}
