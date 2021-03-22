package com.company;

import java.util.Scanner;

public class Ejercicio5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese N:");
        int cantidadMostrar = scanner.nextInt();

        System.out.println("Ingrese M:");
        int cantidadDigitos = scanner.nextInt();

        System.out.println("Ingrese D:");
        int digito = scanner.nextInt();

        while (digito < 0 || digito > 9){
            System.out.println("D debe ser un número entre 0 y 9. Intente nuevamente:");
            digito = scanner.nextInt();
        }

        int b = 0;
        for(Integer i = 0; b < cantidadMostrar ;i++) {
            if(contarDigitos(i, digito) == cantidadDigitos){
                System.out.println(i);
                b++;
            }
        }

        scanner.close();
    }

    public static int contarDigitos(int numero, int digito) {
        int count = 0;

        for(int i = numero; i > 0; i = i/10) {
            if(i%10 == digito){
                count++;
            }
        }

        return count;
    }
}
