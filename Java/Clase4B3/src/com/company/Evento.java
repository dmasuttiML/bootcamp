package com.company;

import java.util.ArrayList;

public class Evento {
    private ArrayList<Invitado> invitados;
    private ArrayList<FuegoArtificial> fuegosArificiales;

    public Evento(ArrayList<Invitado> invitados, ArrayList<FuegoArtificial> fuegosArificiales) {
        this.invitados = invitados;
        this.fuegosArificiales = fuegosArificiales;
    }

    public void ApagarVelas(){
        reventarFuegosArtificiales();
        distribuirTorta();
    }

    private void reventarFuegosArtificiales(){
        for (FuegoArtificial fa: fuegosArificiales) {
            fa.explotar();
        }
    }

    private void distribuirTorta(){
        for (Invitado invitado: invitados) {
            invitado.comerTorta();
        }
    }
}
