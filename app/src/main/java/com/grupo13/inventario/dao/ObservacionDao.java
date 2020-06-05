package com.grupo13.inventario.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Observacion;

import java.util.List;

public interface ObservacionDao {
    @Query("SELECT * FROM Observacion")
    List<Observacion> obtenerObservacion();

    @Query("SELECT * FROM OBSERVACION WHERE catologo_id =:idCatalogo AND toma_id =:idToma")
    Observacion consultarObservacion(String idCatalogo, int idToma);

    @Insert
    void insertarObservacion(Observacion observacion);

    @Update
    void actualizarObservacion(Observacion observacion);

    @Delete
    void elminiarObservacion(Observacion observacion);
}
