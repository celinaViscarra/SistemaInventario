package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @NonNull
    @PrimaryKey
    public String usuario;

    @NonNull
    @ColumnInfo(name = "contrasena")
    public String contra;

    @NonNull
    @ColumnInfo(name = "nombre_usuario")
    public String nomUsuario;

    public Usuario(String usuario, String contra, String nomUsuario){
        this.usuario=usuario;
        this.nomUsuario=nomUsuario;
        this.contra=contra;
    }

    public Usuario(){}
}
