package com.company;

public class Persona implements Precedable<Persona> {
    private String nombre;
    private int dni;

    public Persona(String nombre, int dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int precedeA(Persona t) {
        return t.dni - dni;
    }

    @Override
    public String toString() {
        return nombre + ": " + dni;
    }
}
