package com.company.sorters;

import java.util.Comparator;

public class QuickSortSorterImple<T> implements Sorter<T> {

    @Override
    public void sort(T[] arr, Comparator<T> c) {
        quicksort(arr, 0, arr.length - 1, c);
    }

    private void quicksort(T[] arr, int startIndex, int endIndex, Comparator<T> c)
    {
        if (startIndex < endIndex)
        {
            int pivotIndex = partition(arr, startIndex, endIndex, c);
            quicksort(arr, startIndex, pivotIndex, c);
            quicksort(arr, pivotIndex + 1, endIndex, c);
        }
    }

    private int partition(T[] arr, int startIndex, int endIndex, Comparator<T> c)
    {
        int pivotIndex = (startIndex + endIndex) / 2;
        T pivotValue = arr[pivotIndex];
        startIndex--;
        endIndex++;

        while (true)
        {
            do startIndex++; while (c.compare(arr[startIndex],pivotValue) < 0) ;
            do endIndex--; while (c.compare(arr[endIndex], pivotValue) > 0) ;

            if (startIndex >= endIndex) return endIndex;

            T temp = arr[startIndex];
            arr[startIndex] = arr[endIndex];
            arr[endIndex] = temp;
        }
    }
}
