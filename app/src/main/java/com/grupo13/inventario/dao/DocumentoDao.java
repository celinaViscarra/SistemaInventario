package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.grupo13.inventario.modelo.DetalleAutor;
import com.grupo13.inventario.modelo.Documento;
import java.util.List;

@Dao
public interface DocumentoDao {
    @Query("SELECT * FROM Documento")
    List<Documento> obtenerDocumentos();

    @Query("SELECT * FROM Documento WHERE escrito_id = :escrito_id")
    Documento consultarDocumento(int escrito_id);

    @Insert
    long insertarDocumento(Documento documento);

    @Update
    int actualizarDocumento(Documento documento);

    @Delete
    int eliminarDocumento(Documento documento);

    @Query("DELETE FROM Documento")
    void limpiarTabla();
}
