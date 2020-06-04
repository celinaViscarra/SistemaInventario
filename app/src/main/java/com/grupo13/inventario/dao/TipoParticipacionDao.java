package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.grupo13.inventario.modelo.TipoParticipacion;
import com.grupo13.inventario.modelo.TipoParticipacionConParticipacionDocente;

import java.util.List;

@Dao
public interface TipoParticipacionDao {
    @Query("SELECT * FROM TipoParticipacion")
    List<TipoParticipacion> obtenerTipoParticipaciones();

    @Query("SELECT * FROM TipoParticipacion WHERE participacion_id = :participacion_id")
    TipoParticipacion consultarTipoParticipacion(int participacion_id);

    @Transaction
    @Query("SELECT * FROM TipoParticipacion")
    List<TipoParticipacionConParticipacionDocente> obtenerTipoParticipacionConParticipacionDocente();

    @Transaction
    @Query("SELECT * FROM TipoParticipacion WHERE participacion_id = :participacion_id")
    List<TipoParticipacionConParticipacionDocente> consultarParticipacionesPorTipoParticipacion(int participacion_id);

    @Insert
    void instertarTipoParticipacion(TipoParticipacion tipoParticipacion);

    @Update
    void actualizarTipoParticipacion(TipoParticipacion tipoParticipacion);

    @Delete
    void eliminarTipoParticipacio(TipoParticipacion tipoParticipacion);
}
