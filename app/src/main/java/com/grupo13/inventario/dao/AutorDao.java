package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Autor;
import com.grupo13.inventario.modelo.DetalleAutor;

import java.util.List;

@Dao
public interface AutorDao {
    @Query("SELECT * FROM Autor")
    List<Autor> obtenerAutores();

    @Query("SELECT * FROM Autor WHERE idAutor = :idAutor")
    Autor consultarAutor(int idAutor);

    @Insert
    long insertarAutor(Autor autor);

    @Update
    int actualizarAutor(Autor autor);

    @Delete
    int eliminarAutor(Autor autor);

    @Query("DELETE FROM Autor")
    void limpiarTabla();
}
