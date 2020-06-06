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
    long insertarTomaFisica(TomaFisica tomaFisica);

    @Update
    int actualizarTomaFisica(TomaFisica tomaFisica);

    @Delete
    int eliminarTomaFisica(TomaFisica tomaFisica);
}
