package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Categorias;
import com.grupo13.inventario.modelo.TipoProducto;

import java.util.List;

@Dao
public interface CategoriasDao {
    @Query("SELECT * FROM Categorias")
    List<Categorias> obtenerCategorias();

    @Query("SELECT * FROM Categorias WHERE categoria_id = :categoria_id")
    Categorias consultarCategoria(int categoria_id);

    @Insert
    long insertarCategoria(Categorias categorias);

    @Update
    int actualizarCategorias(Categorias categorias);

    @Delete
    int eliminarCategorias(Categorias categorias);

    @Query("DELETE FROM Categorias")
    void limpiarTabla();
}
