package com.grupo13.inventario.modelo;

public class Horarios {
    int idHora;
    String diaCod;

    public Horarios(int idHora, String diaCod) {
        this.diaCod = diaCod;
        this.idHora = idHora;
    }

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public String getDiaCod() {
        return diaCod;
    }

    public void setDiaCod(String diaCod) {
        this.diaCod = diaCod;
    }
}
