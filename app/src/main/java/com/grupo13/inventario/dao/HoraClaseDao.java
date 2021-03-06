package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.HoraClase;

import java.util.List;

@Dao
public interface HoraClaseDao {
    @Query("SELECT * FROM HoraClase")
    List<HoraClase> obtenerHorasClase();

    @Query("SELECT * FROM HoraClase WHERE hora_id = :hora_id")
    HoraClase consultarHoraClase(int hora_id);

    @Insert
    long insertarHoraClase(HoraClase horaClase);

    @Update
    int actualizarHoraClase(HoraClase horaClase);

    @Delete
    int eliminarHoraClase(HoraClase horaClase);

    @Query("DELETE FROM HoraClase")
    void limpiarTabla();
}
