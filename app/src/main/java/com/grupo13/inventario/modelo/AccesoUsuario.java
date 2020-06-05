package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        indices = @Index( value ={"usuario","id_opcion"}, unique = true),
        foreignKeys = {
                @ForeignKey(
                        entity = OpcionCrud.class,
                        parentColumns = "id_opcion",
                        childColumns = "id_opcion",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Usuario.class,
                        parentColumns = "usuario",
                        childColumns = "usuario",
                        onDelete = CASCADE
                )
})

public class AccesoUsuario {
    @NonNull
    public String usuario;
    @NonNull
    @ColumnInfo(name = "id_opcion")
    public String idOpcion;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_accesousuario")
    public int idAccesoUsuario;

    public  AccesoUsuario(){}

    public AccesoUsuario(String usuario, String idOpcion, int idAccesoUsuario){
        this.idAccesoUsuario=idAccesoUsuario;
        this.idOpcion=idOpcion;
        this.usuario=usuario;
    }
}
