package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.EquipoInformatico;

import java.util.List;

@Dao
public interface EquipoInformaticoDao {
    @Query("SELECT * FROM EquipoInformatico")
    List<EquipoInformatico> obtenerEquiposInformaticos();

    @Query("SELECT * FROM EquipoInformatico WHERE equipo_id = :equipo_id")
    EquipoInformatico consultarEquipoInformatico(int equipo_id);

    @Insert
    long insertarEquipoInformatico(EquipoInformatico equipoInformatico);

    @Update
    int actualizarEquipoInformatico(EquipoInformatico equipoInformatico);

    @Delete
    int eliminarEquipoInformatico(EquipoInformatico equipoInformatico);

    @Query("DELETE FROM EquipoInformatico")
    void limpiarTabla();
}
