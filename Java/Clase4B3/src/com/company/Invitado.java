package com.company;

import java.util.Random;

public class Invitado {
    private String nombre;

    public Invitado(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void comerTorta(){
        try {
            System.out.println(nombre + " está comiendo torta.");
            Thread.sleep(500);
            System.out.println(nombre + " terminó de comer.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
