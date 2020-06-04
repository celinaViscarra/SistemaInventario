package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Docente;
import com.grupo13.inventario.modelo.DocenteConParticipacionesDocentes;

import java.util.List;

@Dao
public interface DocenteDao {
    @Query("SELECT * FROM Docente")
    List<Docente> obtenerDocentes();

    @Query("SELECT * FROM Docente WHERE docentes_id = :docentes_id")
    Docente consultarDocente(int docentes_id);

    @Transaction
    @Query("SELECT * FROM Docente")
    List<DocenteConParticipacionesDocentes> obtenerDocentesConParticipacionesDocentes();

    @Transaction
    @Query("SELECT * FROM Docente WHERE docentes_id = :docentes_id")
    List<DocenteConParticipacionesDocentes> obtenerParticipacionesDocentesPorDocente(int docentes_id);

    @Insert
    void insertarDocente(Docente docente);

    @Update
    void actualizarDocente(Docente docente);

    @Delete
    void eliminarDocente(Docente docente);
}
