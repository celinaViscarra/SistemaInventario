package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.TipoParticipacion;
import java.util.List;

@Dao
public interface TipoParticipacionDao {
    @Query("SELECT * FROM TipoParticipacion")
    List<TipoParticipacion> obtenerTipoParticipaciones();

    @Query("SELECT * FROM TipoParticipacion WHERE participacion_id = :participacion_id")
    TipoParticipacion consultarTipoParticipacion(int participacion_id);

    @Insert
    long instertarTipoParticipacion(TipoParticipacion tipoParticipacion);

    @Update
    int actualizarTipoParticipacion(TipoParticipacion tipoParticipacion);

    @Delete
    int eliminarTipoParticipacio(TipoParticipacion tipoParticipacion);

    @Query("DELETE FROM TipoParticipacion")
    void limpiarTabla();
}
