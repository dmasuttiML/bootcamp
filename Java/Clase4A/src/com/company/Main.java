package com.company;

import com.company.sorters.BubbleSortSorterImple;
import com.company.sorters.HeapSortImple;
import com.company.sorters.QuickSortSorterImple;
import com.company.sorters.Sorter;

import java.util.Comparator;

public class Main {

    private static <T> void mostrar(T arr[]){
        for (T a: arr) {
            System.out.println(a);
        }
    }
    public static void main(String[] args) {
        int len = 100000;
        Integer[] arr = new Integer[len];
        for (int i = 0; i < len; i++) {
            arr[i] = len - i;
        }

        Sorter<Integer> sorter = (Sorter<Integer>) MiFactory.getInstance("MiFactory.properties");
        Comparator<Integer> c = (a, b) -> a - b;
        Timer timer = new Timer();

        timer.start();
        sorter.sort(arr, c);
        timer.stop();

        mostrar(arr);
        System.out.println("Tiempo empleado: " + timer.elapsedTime() + " milisegundos");
    }
}
