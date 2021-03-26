package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        GuardaRopa guardaRopa = new GuardaRopa();
        ArrayList<Prenda> prendas = new ArrayList<Prenda>();
        prendas.add(new Prenda("Nike", "Pantalon"));
        prendas.add(new Prenda("Adidas", "Remera"));

        guardaRopa.mostrarPrendas();
        int codigo = guardaRopa.guardarPrendas(prendas);
        guardaRopa.mostrarPrendas();

        System.out.println("Codigo obtenido: " + codigo);

        ArrayList<Prenda> prendasDevueltas = guardaRopa.devolverPrendas(codigo);
        guardaRopa.mostrarPrendas();

        System.out.println("Prendas devueltas:");
        for (Prenda prenda: prendasDevueltas) {
            System.out.println(prenda);
        }
    }
}
