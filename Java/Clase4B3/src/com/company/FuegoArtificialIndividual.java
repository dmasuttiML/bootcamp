package com.company;

public class FuegoArtificialIndividual implements FuegoArtificial {

    private String sonido;

    public FuegoArtificialIndividual(String sonido) {
        this.sonido = sonido;
    }

    public String getSonido() {
        return sonido;
    }

    public void setSonido(String sonido) {
        this.sonido = sonido;
    }

    @Override
    public void explotar() {
        System.out.println(sonido);
    }
}
