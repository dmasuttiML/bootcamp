package com.company;

import java.util.ArrayList;

public class DemoEvento {
    public static void main(String[] args) {
        ArrayList<Invitado> invitados = new ArrayList<Invitado>();

        invitados.add(new Invitado("Juan"));
        invitados.add(new Invitado("Pedro"));
        invitados.add(new Invitado("Lucas"));
        invitados.add(new Invitado("Javier"));
        invitados.add(new InvitadoMeLi("Diego"));
        invitados.add(new InvitadoMeLi("Franco"));
        invitados.add(new InvitadoMeLi("Ramon"));
        invitados.add(new InvitadoMeLi("Ivan"));

        ArrayList<FuegoArtificial> fuegoArtificiales = new ArrayList<>();

        FuegoArtificialPack pack = new FuegoArtificialPack();
        FuegoArtificialPack subPack1 = new FuegoArtificialPack();
        FuegoArtificialPack subPack2 = new FuegoArtificialPack();

        subPack1.add(new FuegoArtificialIndividual("pam!"));
        subPack1.add(new FuegoArtificialIndividual("pammm!"));

        subPack2.add(new FuegoArtificialIndividual("pem!"));
        subPack2.add(new FuegoArtificialIndividual("pemmm!"));

        pack.add(subPack1);
        pack.add(subPack2);

        fuegoArtificiales.add(pack);
        fuegoArtificiales.add(new FuegoArtificialIndividual("pim!"));
        fuegoArtificiales.add(new FuegoArtificialIndividual("pom!"));
        fuegoArtificiales.add(new FuegoArtificialIndividual("pum!"));

        Evento evento = new Evento(invitados, fuegoArtificiales);

        evento.ApagarVelas();
    }
}
