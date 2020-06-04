package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Documento;
import com.grupo13.inventario.modelo.TipoProducto;
import com.grupo13.inventario.modelo.TipoProductoConDocumentos;

import java.util.List;

@Dao
public interface TipoProductoDao {
    @Query("SELECT * FROM TipoProducto")
    List<TipoProducto> obtenerTipos();

    @Query("SELECT * FROM TipoProducto WHERE tipo_producto_id = :tipo_producto_id")
    TipoProducto consultarTipoProducto(int tipo_producto_id);

    @Transaction
    @Query("SELECT * FROM TipoProducto")
    List<TipoProductoConDocumentos> obtenerTiposConDocumentos();

    @Transaction
    @Query("SELECT * FROM TipoProducto WHERE tipo_producto_id = :tipo_producto_id")
    List<TipoProductoConDocumentos> consultarDocumentosPorTipoProducto(int tipo_producto_id);

    @Insert
    void insertarTipoProducto(TipoProducto tipoProducto);

    @Update
    void actualizarTipoProducto(TipoProducto tipoProducto);

    @Delete
    void eliminarTipoProducto(TipoProducto tipoProducto);
}
