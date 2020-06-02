package com.grupo13.inventario;

public class AccesoUsuario {
    String usuario, idOpcion;
    int idAccesoUsuario;

    public AccesoUsuario(String u, String i, int id){
        this.idAccesoUsuario=id;
        this.idOpcion=i;
        this.usuario=u;
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
