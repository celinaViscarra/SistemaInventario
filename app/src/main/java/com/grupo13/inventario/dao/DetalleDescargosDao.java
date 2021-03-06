package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.DetalleDescargos;

import java.util.List;
@Dao
public interface DetalleDescargosDao {
    @Query("SELECT * FROM DetalleDescargos")
    List<DetalleDescargos> obtenerDetalleDescargos();

    @Query("SELECT * FROM detalledescargos WHERE equipo_id=:idEquipo AND descargo_id=:idDescargo")
    DetalleDescargos consultarDetalleDescargos(int idEquipo, int idDescargo);

    @Insert
    long insertarDetalleDescargos(DetalleDescargos detalleDescargos);

    @Update
    int actualizarDetalleDescargos(DetalleDescargos detalleDescargos);

    @Delete
    int eliminarDetalleDescargos(DetalleDescargos detalleDescargos);

    @Query("DELETE FROM DetalleDescargos")
    void limpiarTabla();
}
