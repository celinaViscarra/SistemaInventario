package com.grupo13.inventario;

public class ParticipacionDocente {
    int idDocentes, idEscritos, idParticipacion;

    public ParticipacionDocente(int id1, int id2, int id3){
        this.idDocentes=id1;
        this.idEscritos=id2;
        this.idParticipacion=id3;
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
