package com.grupo13.inventario;

public class Horarios {
    int idHora;
    String diaCod;

    public Horarios(int id, String dia){
        this.diaCod=dia;
        this.idHora=id;
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
