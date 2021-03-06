package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.OpcionCrud;

import java.util.List;
@Dao
public interface OpcionCrudDao {
    @Query("SELECT * FROM OpcionCrud")
    List<OpcionCrud> obtenerOpcionCrud();

    @Query("SELECT * FROM OpcionCrud WHERE id_opcion =:idOpcion")
    OpcionCrud consultarOpcionCrud(String idOpcion);

    @Insert
    long insertarOpcionCrud(OpcionCrud opcionCrud);

    @Update
    int actualizarOpcionCrud(OpcionCrud opcionCrud);

    @Delete
    int eliminarOpcionCrud(OpcionCrud opcionCrud);

    @Query("DELETE FROM OpcionCrud")
    void limpiarTabla();
}
