package com.company;

import java.util.ArrayList;

public class Carrera {
    private float distancia;
    private float premioEnDolares;
    private String nombre;
    private int cantidadDeVehiculosPermitidos;
    private ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private SocorristaAuto socorristaAuto = new SocorristaAuto();
    private SocorristaMoto socorristaMoto = new SocorristaMoto();

    public Carrera(float distancia, float premioEnDolares, String nombre, int cantidadDeVehiculosPermitidos){
        this.distancia = distancia;
        this.premioEnDolares = premioEnDolares;
        this.nombre = nombre;
        this.cantidadDeVehiculosPermitidos = cantidadDeVehiculosPermitidos;
    }

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

    private Vehiculo buscarPorPatente(String patente) {
        return vehiculos.stream()
                        .filter(v -> v.getPatente().equals(patente))
                        .findAny()
                        .orElse(null);
    }

    public void eliminarVehiculo(Vehiculo vehiculo){
        vehiculos.remove(vehiculo);
    }

    public void eliminarVehiculoConPatenete(String patente){
        eliminarVehiculo(buscarPorPatente(patente));
    }

    public Vehiculo obtenerGanador(){
        Vehiculo ganador = null;

        for (Vehiculo v: vehiculos) {
            if(ganador == null || v.obtenerScore() > ganador.obtenerScore()){
                ganador = v;
            }
        }

        return ganador;
    }

    public void socorrerAuto(String patente){
        Vehiculo vehiculo = buscarPorPatente(patente);

        if(vehiculo != null && vehiculo.getClass().getSimpleName() == "Auto"){
            socorristaAuto.socorrer((Auto) vehiculo);
        }
        else {
            System.out.println("El auto a socorrer no se encuentra en la lista: " + patente);
        }
    }

    public void socorrerMoto(String patente){
        Vehiculo vehiculo = buscarPorPatente(patente);

        if(vehiculo != null && vehiculo.getClass().getSimpleName() == "Moto"){
            socorristaMoto.socorrer((Moto) vehiculo);
        }
        else {
            System.out.println("La moto a socorrer no se encuentra en la lista: " + patente);
        }
    }
}
