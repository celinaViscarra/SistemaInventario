package com.grupo13.inventario;

public class AccesoUsuario {
    String usuario, idOpcion;
    int idAccesoUsuario;

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
