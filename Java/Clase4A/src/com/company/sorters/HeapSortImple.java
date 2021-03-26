package com.company.sorters;

import java.util.Comparator;

public class HeapSortImple<T> implements Sorter<T> {
    @Override
    public void sort(T[] arr, Comparator<T> c) {
        int size = arr.length;

        for (int i = size / 2 - 1; i >= 0; i--)
            heapify(arr, size, i, c);

        for (int i = size - 1; i >= 0; i--)
        {
            T temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0, c);
        }
    }

    private void heapify(T[] array, int size, int i, Comparator<T> c)
    {
        int max   = i;
        int left  = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && c.compare(array[left], array[max]) > 0)
            max = left;

        if (right < size && c.compare(array[right], array[max]) > 0)
            max = right;

        if (max != i)
        {
            T temp = array[i];
            array[i] = array[max];
            array[max] = temp;

            heapify(array, size, max, c);
        }
    }
}
