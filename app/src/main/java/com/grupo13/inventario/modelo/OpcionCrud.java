package com.grupo13.inventario.modelo;

public class OpcionCrud {
    String idOpcion, desOpcion;
    int numCrud;

    public OpcionCrud(String id, String des, int num){
        this.idOpcion=id;
        this.desOpcion=des;
        this.numCrud=num;
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getDesOpcion() {
        return desOpcion;
    }

    public void setDesOpcion(String desOpcion) {
        this.desOpcion = desOpcion;
    }

    public int getNumCrud() {
        return numCrud;
    }

    public void setNumCrud(int numCrud) {
        this.numCrud = numCrud;
    }
}
