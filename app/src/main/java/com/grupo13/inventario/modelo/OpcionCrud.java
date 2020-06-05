package com.grupo13.inventario.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class OpcionCrud {
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "id_opcion")
    public String idOpcion;
    @NonNull
    public String desOpcion;
    @NonNull
    public int numCrud;

    public OpcionCrud(String id, String des, int num){
        this.idOpcion=id;
        this.desOpcion=des;
        this.numCrud=num;
    }

    @Ignore
    public OpcionCrud(){}
}
