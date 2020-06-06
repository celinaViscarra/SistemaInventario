package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Sustituciones;

import java.util.List;

@Dao
public interface SustitucionesDao {
    @Query("SELECT * FROM Sustituciones")
    List<Sustituciones> obtenerSustituciones();

    @Query("SELECT * FROM Sustituciones WHERE sustitucion_id= :idSustituciones")
    Sustituciones consultarSustituciones(int idSustituciones);

    @Insert
    long insertarSustituciones(Sustituciones sustituciones);

    @Update
    int actualizarSustituciones(Sustituciones sustituciones);

    @Delete
    int eliminarSustituciones(Sustituciones sustituciones);
}
