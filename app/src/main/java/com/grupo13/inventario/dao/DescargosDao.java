package com.grupo13.inventario.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Descargos;

import java.util.List;

public interface DescargosDao {
    @Query("SELECT * FROM Descargos")
    List<Descargos> obtenerDescargos();

    @Query("SELECT * FROM Descargos WHERE descargo_id=:idDescargo")
    Descargos consultarDescargos(int idDescargo);

    @Insert
    void insertarDescargos(Descargos descargos);

    @Update
    void actualizarDescargos(Descargos descargos);

    @Delete
    void eliminarDescargos(Descargos descargos);
}
