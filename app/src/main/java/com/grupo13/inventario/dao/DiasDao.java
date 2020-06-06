package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Dias;

import java.util.List;

@Dao
public interface DiasDao {
    @Query("SELECT * FROM Dias")
    List<Dias> obtenerDias();

    @Query("SELECT * FROM Dias WHERE dia_cod = :dia_cod")
    Dias consultarDia(String dia_cod);

    @Insert
    long insertarDia(Dias dia);

    @Update
    int actualizarDia(Dias dia);

    @Delete
    int eliminarDia(Dias dia);
}
