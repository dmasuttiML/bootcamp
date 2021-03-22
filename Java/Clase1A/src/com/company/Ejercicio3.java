package com.company;

import java.util.Scanner;

public class Ejercicio3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese N:");

        int n = scanner.nextInt();

        boolean esPrimo = esPrimo(n);

        System.out.println(esPrimo ? "Es primo" : "No es primo");

        scanner.close();
    }

    public static boolean esPrimo(int n){
        boolean flag = n != 0;

        for (int i = 2; i < n ; i++) {
            if(n%i == 0){
                flag = false;
                break;
            }
        }

        return flag;
    }
}
