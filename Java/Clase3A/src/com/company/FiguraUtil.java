package com.company;

public class FiguraUtil {
    public static double areaPromedio(FiguraGeometrica[] arr) {
        double sum = 0;

        for (FiguraGeometrica figura : arr) {
            sum += figura.area();
        }

        return sum / arr.length;
    }

    public static void main(String[] args) {
        FiguraGeometrica[] arr = new FiguraGeometrica[3];

        arr[0] = new Circulo(4);
        arr[1] = new Triangulo(2, 5);
        arr[2] = new Rectangulo(3, 4);

        System.out.println("El area promedio es " + areaPromedio(arr));
    }
}
