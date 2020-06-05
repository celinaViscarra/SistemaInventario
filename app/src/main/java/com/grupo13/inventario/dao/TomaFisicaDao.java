package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.TomaFisica;

import java.util.List;

@Dao
public interface TomaFisicaDao {
    @Query("SELECT * FROM TomaFisica")
    List<TomaFisica> obtenerTomaFisica();

    @Query("SELECT * FROM TomaFisica WHERE toma_id=:idToma")
    TomaFisica consultarTomaFisica(int idToma);

    @Insert
    void insertarTomaFisica(TomaFisica tomaFisica);

    @Update
    void actualizarTomaFisica(TomaFisica tomaFisica);

    @Delete
    void eliminarTomaFisica(TomaFisica tomaFisica);
}
