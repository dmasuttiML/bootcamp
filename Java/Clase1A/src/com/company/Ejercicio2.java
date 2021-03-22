package com.company;

import java.util.Scanner;

public class Ejercicio2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese N:");
        int n = scanner.nextInt();

        System.out.println("Ingrese M:");
        int m = scanner.nextInt();

        int b = 0;

        for(int i = 0; b < n;i++){
            if(i%m == 0){
                System.out.println(i);
                b++;
            }
        }

        scanner.close();
    }
}
