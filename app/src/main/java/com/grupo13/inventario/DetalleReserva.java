package com.grupo13.inventario;

public class DetalleReserva {
    String diaCod;
    int idHora, idPrestamos;

    public DetalleReserva(String dia, int idh, int idp){
        this.diaCod=dia;
        this.idHora=idh;
        this.idPrestamos=idp;
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
