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
    long insertarParticipacionDocente(ParticipacionDocente participacionDocente);

    @Update
    int actualizarParticipacionDocente(ParticipacionDocente participacionDocente);

    @Delete
    int eliminarParticipacionDocente(ParticipacionDocente participacionDocente);

    @Query("DELETE FROM ParticipacionDocente")
    void limpiarTabla();
}
