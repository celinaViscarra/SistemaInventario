package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.grupo13.inventario.modelo.DetalleAutor;
import com.grupo13.inventario.modelo.Documento;
import com.grupo13.inventario.modelo.DocumentoConDetalleAutor;
import com.grupo13.inventario.modelo.DocumentoConParticipacionesDocentes;

import java.util.List;

@Dao
public interface DocumentoDao {
    @Query("SELECT * FROM Documento")
    List<Documento> obtenerDocumentos();

    @Query("SELECT * FROM Documento WHERE escrito_id = :escrito_id")
    Documento consultarDocumento(int escrito_id);

    @Transaction
    @Query("SELECT * FROM Documento")
    List<DocumentoConDetalleAutor> obtenerDetallesAutorPorDocumento();

    @Transaction
    @Query("SELECT * FROM Documento WHERE escrito_id = :escrito_id")
    List<DocumentoConDetalleAutor> consultarDetallesAutorPorIdioma(int escrito_id);

    @Transaction
    @Query("SELECT * FROM Documento")
    List<DocumentoConParticipacionesDocentes> obtenerParticipacionesDocentesPorDocumentos();

    @Transaction
    @Query("SELECT * FROM Documento WHERE escrito_id = :escrito_id")
    List<DocumentoConParticipacionesDocentes> consultarParticipacionesDocentesPorDocumento(int escrito_id);

    @Insert
    void insertarDocumento(Documento documento);

    @Update
    void actualizarDocumento(Documento documento);

    @Delete
    void eliminarDocumento(Documento documento);
}
