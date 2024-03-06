package org.prashp.lib19.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortTest {
    @Test public void testBubbleSort()
    {
        int[] unsorted = {5,4,3,2,1,0};
        BubbleSort sort = new BubbleSort();

        sort.sort(unsorted);
        assertTrue(isSorted(unsorted));

    }


    @Test public void testRandomBubbleSort()
    {
        int[] unsorted = generateRandomArray(100);
        BubbleSort sort = new BubbleSort();

        sort.sort(unsorted);
        assertTrue(isSorted(unsorted));

    }
    private static boolean isSorted(int[] data)
    {
        for(int i=0; i<data.length-1; i++) {
            if (data[i] > data[i+1]) return false;
        }
        return true;
    }
    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 100); // Generate random numbers between 0 and 100
        }
        return arr;
    }
}
