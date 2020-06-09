package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.AccesoUsuario;

import java.util.List;

@Dao
public interface AccesoUsuarioDao {
    @Query("SELECT * FROM AccesoUsuario")
    List<AccesoUsuario> obtenerAccesoUsuario();

    @Query("SELECT * FROM AccesoUsuario WHERE id_opcion = :id_opcion AND usuario = :usuario")
    AccesoUsuario consultarAccesoUsuario(String id_opcion, String usuario);

    @Insert
    long insertarAccesoUsuario(AccesoUsuario accesoUsuario);

    @Update
    int actualizarAccesoUsuario(AccesoUsuario accesoUsuario);

    @Delete
    int eliminarAccesoUsuario(AccesoUsuario accesoUsuario);

    @Query("DELETE FROM AccesoUsuario")
    void limpiarTabla();
}
