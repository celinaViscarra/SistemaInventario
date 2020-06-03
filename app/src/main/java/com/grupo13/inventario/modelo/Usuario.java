package com.grupo13.inventario.modelo;

public class Usuario {
    String usuario, contra, nomUsuario;

    public Usuario(String usuario, String contra, String nomUsuario){
        this.usuario=usuario;
        this.nomUsuario=nomUsuario;
        this.contra=contra;
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
