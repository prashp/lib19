package org.prashp.lib19.sort;

public class BubbleSort implements Sortable{
    @Override
    public void sort(int[] a)
    {
        for (int i = 0; i  < a.length; i++)
        {
            for (int j = i+1; j < a.length; j++)
            {
                if (a[j] < a[i])
                {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }
}
