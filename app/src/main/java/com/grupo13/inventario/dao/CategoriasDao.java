package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Categorias;

import java.util.List;

@Dao
public interface CategoriasDao {
    @Query("SELECT * FROM Categorias")
    List<Categorias> obtenerCategorias();

    @Query("SELECT * FROM Categorias WHERE categoria_id = :categoria_id")
    Categorias consultarCategoria(int categoria_id);

    @Insert
    void insertarCategoria(Categorias categorias);

    @Update
    void actualizarCategorias(Categorias categorias);

    @Delete
    void eliminarCategorias(Categorias categorias);
}
