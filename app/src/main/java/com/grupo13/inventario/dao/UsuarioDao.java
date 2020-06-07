package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM Usuario")
    List<Usuario> obtenerUsuarios();

    @Query("SELECT * FROM Usuario WHERE usuario =:usuario")
    Usuario consultarUsuario(String usuario);

    @Insert
    long insertarUsuario(Usuario usuario);

    @Update
    int actualizarUsuario(Usuario usuario);

    @Delete
    int eliminarUsuario(Usuario usuario);

    @Query("DELETE FROM Usuario")
    void limpiarTabla();
}
