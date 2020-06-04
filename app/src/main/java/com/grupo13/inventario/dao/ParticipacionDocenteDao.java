package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.ParticipacionDocente;

import java.util.List;

@Dao
public interface ParticipacionDocenteDao {
    @Query("SELECT * FROM ParticipacionDocente")
    List<ParticipacionDocente> obtenerParticipacionesDocente();

    @Query("SELECT * FROM ParticipacionDocente WHERE escrito_id = :escrito_id AND docentes_id = :docentes_id")
    ParticipacionDocente consultarParticipacionDocente(int escrito_id, int docentes_id);

    @Insert
    void insertarParticipacionDocente(ParticipacionDocente participacionDocente);

    @Update
    void actualizarParticipacionDocente(ParticipacionDocente participacionDocente);

    @Delete
    void eliminarParticipacionDocente(ParticipacionDocente participacionDocente);
}