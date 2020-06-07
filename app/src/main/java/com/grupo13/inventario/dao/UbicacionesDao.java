package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Ubicaciones;

import java.util.List;

@Dao
public interface UbicacionesDao {
    @Query("SELECT * FROM Ubicaciones")
    List<Ubicaciones> obtenerUbicaciones();

    @Query("SELECT * FROM Ubicaciones WHERE ubicacion_id=:idUbicacion")
    Ubicaciones consultarUbicaciones(int idUbicacion);

    @Insert
    long insertarUbicaciones(Ubicaciones ubicaciones);

    @Update
    int actualizarUbicaciones(Ubicaciones ubicaciones);

    @Delete
    int eliminarUbicaciones(Ubicaciones ubicaciones);

    @Query("DELETE FROM Ubicaciones")
    void limpiarTabla();

}
