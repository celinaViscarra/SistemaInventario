package com.grupo13.inventario;

import java.sql.Time;

public class HoraClase {
    int idHora;
    Time horaInicio, horaFin;

    public HoraClase(){}

    public HoraClase(int id, Time hi, Time hf){
        this.horaFin=hf;
        this.horaInicio=hi;
        this.idHora=id;
    }

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
}
