package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Marca;

import java.util.List;
@Dao
public interface MarcaDao {
    @Query("SELECT * FROM Marca")
    List<Marca> obtenerMarcas();

    @Query("SELECT * FROM Marca WHERE marca_id=:idMarca")
    Marca consultarMarca(String idMarca);

    @Insert
    long insertarMarca(Marca marca);

    @Update
    void actualizarMarca(Marca marca);

    @Delete
    void eliminarMarca(Marca marca);
}
