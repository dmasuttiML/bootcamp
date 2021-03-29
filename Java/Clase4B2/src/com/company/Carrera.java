package com.company;

import java.util.ArrayList;

public class Carrera {
    private float distancia;
    private float premioEnDolares;
    private String nombre;
    private int cantidadDeVehiculosPermitidos;
    private ArrayList<Vehiculo> vehiculos;

    public void darDeAltaAuto(float velocidad, float aceleracion, int anguloDeGiro, String patente){
        if(cantidadDeVehiculosPermitidos > vehiculos.size()){
            vehiculos.add(new Auto(velocidad, aceleracion, anguloDeGiro, patente));
        }
        else {
            System.out.println("No queda cupo en la carrera para el auto: " + patente);
        }
    }

    public void darDeAltaMoto(float velocidad, float aceleracion, int anguloDeGiro, String patente){
        if(cantidadDeVehiculosPermitidos > vehiculos.size()){
            vehiculos.add(new Moto(velocidad, aceleracion, anguloDeGiro, patente));
        }
        else{
            System.out.println("No queda cupo en la carrera para la moto: " + patente);
        }
    }

    public void eliminarVehiculo(Vehiculo vehiculo){
        vehiculos.remove(vehiculo);
    }

    public void  eliminarVehiculoConPatenete(String patente){
        vehiculos.removeIf(v -> v.getPatente().equals(patente));
    }

    public Vehiculo obtenerGanador(){
        Vehiculo ganador = null;

        for (Vehiculo v: vehiculos) {
            if(ganador == null || v.obtenerScore() > v.obtenerScore()){
                ganador = v;
            }
        }

        return ganador;
    }

}
