package com.grupo13.inventario.modelo;

public class DetalleReserva {
    String diaCod;
    int idHora, idPrestamos;

    public DetalleReserva(String diaCod, int idHora, int idPrestamos){
        this.diaCod=diaCod;
        this.idHora=idHora;
        this.idPrestamos=idPrestamos;
    }

    public String getDiaCod() {
        return diaCod;
    }

    public void setDiaCod(String diaCod) {
        this.diaCod = diaCod;
    }

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public int getIdPrestamos() {
        return idPrestamos;
    }

    public void setIdPrestamos(int idPrestamos) {
        this.idPrestamos = idPrestamos;
    }
}
