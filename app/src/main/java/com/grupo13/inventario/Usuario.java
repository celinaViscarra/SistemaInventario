package com.grupo13.inventario;

public class Usuario {
    String usuario, contra, nomUsuario;

    public Usuario(String u, String c, String n){
        this.usuario=u;
        this.nomUsuario=n;
        this.contra=c;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
}
