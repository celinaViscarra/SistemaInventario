package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Motivo;

import java.util.List;

@Dao
public interface MotivoDao {
    @Query("SELECT * FROM Motivo")
    List<Motivo> obtenerMotivos();

    @Query("SELECT * FROM Motivo WHERE motivo_id=:idMotivo")
    Motivo consultarMotivo(int idMotivo);

    @Insert
    void insertarMotivo(Motivo motivo);

    @Update
    void actualizarMotivo(Motivo motivo);

    @Delete
    void eliminarMotivo(Motivo motivo);
}
