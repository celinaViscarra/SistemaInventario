package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Autor;
import com.grupo13.inventario.modelo.AutorConDetalleAutor;
import com.grupo13.inventario.modelo.DetalleAutor;

import java.util.List;

@Dao
public interface AutorDao {
    @Query("SELECT * FROM Autor")
    List<Autor> obtenerAutores();

    @Query("SELECT * FROM Autor WHERE idAutor = :idAutor")
    Autor consultarAutor(int idAutor);

    @Query("SELECT * FROM Autor")
    List<AutorConDetalleAutor> obtenerDetalleAutorPorAutores();

    @Query("SELECT * FROM Autor WHERE idAutor = :idAutor")
    List<AutorConDetalleAutor> consultarDetalleAutorPorAutor(int idAutor);

    @Insert
    void insertarAutor(Autor autor);

    @Update
    void actualizarAutor(Autor autor);

    @Delete
    void eliminarAutor(Autor autor);
}
