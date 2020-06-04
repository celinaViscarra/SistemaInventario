package com.grupo13.inventario.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccesoUsuario {
    @PrimaryKey(autoGenerate = true)

    public String usuario;

    @ColumnInfo(name = "id_opcion")
    public String idOpcion;
    @ColumnInfo(name = "id_accesousuario")
    public int idAccesoUsuario;

    public AccesoUsuario(String usuario, String idOpcion, int idAccesoUsuario){
        this.idAccesoUsuario=idAccesoUsuario;
        this.idOpcion=idOpcion;
        this.usuario=usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public int getIdAccesoUsuario() {
        return idAccesoUsuario;
    }

    public void setIdAccesoUsuario(int idAccesoUsuario) {
        this.idAccesoUsuario = idAccesoUsuario;
    }
}
