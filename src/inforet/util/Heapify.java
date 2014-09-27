package inforet.util;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by johnuiterwyk on 27/09/2014.
 *
 */
public class Heapify <T extends Comparable> {
    public void sortList(ArrayList<T> items)
    {
        for (int i = Math.floor(items.size / 2) ; i >= 1 ; i--)
        {
            heapify (items, i);
        }

    }

    private void heapify(ArrayList<T> items, int position)
    {
        int childPosition = 2 * position;              // child_pos = left child

        while (child_pos <= size(P))
// if i has two children, check if left or right is smaller
            if (child_pos < size(P) ) && (P[child_pos] > P[child_pos+1])
        child_pos++
//child_pos = right child
// if i < smaller child
        if (P[i] <= P[child_pos])
            break    // exit loop
                    // else swap i and smaller child
                    swap items at P[i] and P[child_pos]
        else
        i = child_pos
        child_pos = 2 * i
    }
}
