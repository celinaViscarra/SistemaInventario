package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Observacion;

import java.util.List;

@Dao
public interface ObservacionDao {
    @Query("SELECT * FROM Observacion")
    List<Observacion> obtenerObservacion();

    @Query("SELECT * FROM OBSERVACION WHERE catalogo_id =:idCatalogo AND toma_id =:idToma")
    Observacion consultarObservacion(String idCatalogo, int idToma);

    @Insert
    long insertarObservacion(Observacion observacion);

    @Update
    void actualizarObservacion(Observacion observacion);

    @Delete
    void elminiarObservacion(Observacion observacion);
}
