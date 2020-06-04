package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.DetalleAutor;

import java.util.List;

@Dao
public interface DetalleAutorDao {
    @Query("SELECT * FROM DetalleAutor")
    List<DetalleAutor> obtenerDetalles();

    @Query("SELECT * FROM DetalleAutor WHERE idAutor = :idAutor AND escrito_id = :escrito_id")
    DetalleAutor consultarDetalle(int idAutor, int escrito_id);

    @Insert
    void insertarDetalleAutor(DetalleAutor detalleAutor);

    @Update
    void actualizarDetalleAutor(DetalleAutor detalleAutor);

    @Delete
    void eliminarDetalleAutor(DetalleAutor detalleAutor);
}
