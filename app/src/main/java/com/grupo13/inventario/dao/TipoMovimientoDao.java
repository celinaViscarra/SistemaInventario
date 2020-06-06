package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.TipoMovimiento;

import java.util.List;

@Dao
public interface TipoMovimientoDao {
    @Query("SELECT * FROM TipoMovimiento")
    List<TipoMovimiento> obtenerTipoMoviento();

    @Query("SELECT *FROM TipoMovimiento WHERE tipo_movimiento_id = :tipo_movimiento_id ")
    TipoMovimiento consultarTipoMovimiento(int tipo_movimiento_id);

    @Insert
    long insertarTipoMovimiento(TipoMovimiento tipoMovimiento);

    @Update
    int actualizarTipoMovimiento(TipoMovimiento tipoMovimiento);

    @Delete
    int eliminarTipoMovimiento(TipoMovimiento tipoMovimiento);
}
