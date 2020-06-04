package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Horarios;

import java.util.List;

@Dao
public interface HorariosDao {
    @Query("SELECT * FROM Horarios")
    List<Horarios> obtenerHorarios();

    @Query("SELECT * FROM Horarios WHERE hora_id = :hora_id AND dia_cod = :dia_cod")
    Horarios consultarHorario(int hora_id, String dia_cod);

    @Insert
    void insertarHorario(Horarios horario);

    @Update
    void actualizarHorario(Horarios horario);

    @Delete
    void eliminarHorario(Horarios horario);
}
