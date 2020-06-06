package com.grupo13.inventario.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.inventario.modelo.Idiomas;
import java.util.List;

@Dao
public interface IdiomasDao {
    @Query("SELECT * FROM Idiomas")
    List<Idiomas> obtenerIdiomas();

    @Query("SELECT * FROM Idiomas WHERE idioma_id = :idioma_id")
    Idiomas consultarIdioma(int idioma_id);

    @Insert
    long insertarIdioma(Idiomas idioma);

    @Update
    int actualizarIdioma(Idiomas idioma);

    @Delete
    int eliminarIdioma(Idiomas idioma);
}
