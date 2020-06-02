package com.grupo13.inventario;

public class ParticipacionDocente {
    int idDocentes, idEscritos, idParticipacion;

    public ParticipacionDocente(int idDocentes, int idEscritos, int idParticipacion){
        this.idDocentes=idDocentes;
        this.idEscritos=idEscritos;
        this.idParticipacion=idParticipacion;
    }

    public int getIdDocentes() {
        return idDocentes;
    }

    public void setIdDocentes(int idDocentes) {
        this.idDocentes = idDocentes;
    }

    public int getIdEscritos() {
        return idEscritos;
    }

    public void setIdEscritos(int idEscritos) {
        this.idEscritos = idEscritos;
    }

    public int getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(int idParticipacion) {
        this.idParticipacion = idParticipacion;
    }
}
