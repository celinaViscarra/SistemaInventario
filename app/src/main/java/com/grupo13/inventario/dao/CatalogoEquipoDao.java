package com.grupo13.inventario.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.CatalogoEquipo;

import java.util.List;

public interface CatalogoEquipoDao {
    @Query("SELECT * FROM CatalogoEquipo")
    List<CatalogoEquipo> obtenerCatalogoEquipo();

    @Query("SELECT * FROM CatalogoEquipo WHERE catalogo_id=:idCatalogo")
    CatalogoEquipo consultarCatalogoEquipo(String idCatalogo);

    @Insert
    void insertarCatalogoEquipo(CatalogoEquipo catalogoEquipo);

    @Update
    void actualizarCatalogoEquipo(CatalogoEquipo catalogoEquipo);

    @Delete
    void eliminarCatalogoEquipo(CatalogoEquipo catalogoEquipo);
}
