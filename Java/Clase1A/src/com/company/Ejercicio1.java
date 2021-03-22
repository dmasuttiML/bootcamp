package com.company;

import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese N:");

        int n = scanner.nextInt();
        int b = 0;

        for(int i = 0; b < n;i++){
            if(i%2 == 0){
                System.out.println(i);
                b++;
            }
        }

        scanner.close();
    }
}
