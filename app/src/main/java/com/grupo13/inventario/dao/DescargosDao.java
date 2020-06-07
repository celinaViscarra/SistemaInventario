package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Descargos;

import java.util.List;
@Dao
public interface DescargosDao {
    @Query("SELECT * FROM Descargos")
    List<Descargos> obtenerDescargos();

    @Query("SELECT * FROM Descargos WHERE descargo_id=:idDescargo")
    Descargos consultarDescargos(int idDescargo);

    @Insert
    long insertarDescargos(Descargos descargos);

    @Update
    int actualizarDescargos(Descargos descargos);

    @Delete
    int eliminarDescargos(Descargos descargos);

    @Query("DELETE FROM Descargos")
    void limpiarTabla();
}
