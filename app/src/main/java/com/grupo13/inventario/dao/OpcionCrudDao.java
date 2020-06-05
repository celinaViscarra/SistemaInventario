package com.grupo13.inventario.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.OpcionCrud;

import java.util.List;

public interface OpcionCrudDao {
    @Query("SELECT * FROM OpcionCrud")
    List<OpcionCrud> obtenerOpcionCrud();

    @Query("SELECT * FROM OpcionCrud WHERE id_opcion =:idOpcion")
    OpcionCrud consultarOpcionCrud(String idOpcion);

    @Insert
    void insertarOpcionCrud(OpcionCrud opcionCrud);

    @Update
    void actualizarOpcionCrud(OpcionCrud opcionCrud);

    @Delete
    void eliminarOpcionCrud(OpcionCrud opcionCrud);
}
