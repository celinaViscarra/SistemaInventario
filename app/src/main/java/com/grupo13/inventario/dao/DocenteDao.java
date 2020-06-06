package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Docente;

import java.util.List;

@Dao
public interface DocenteDao {
    @Query("SELECT * FROM Docente")
    List<Docente> obtenerDocentes();

    @Query("SELECT * FROM Docente WHERE docentes_id = :docentes_id")
    Docente consultarDocente(int docentes_id);

    @Insert
    long insertarDocente(Docente docente);

    @Update
    int actualizarDocente(Docente docente);

    @Delete
    int eliminarDocente(Docente docente);
}
