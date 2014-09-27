package inforet.util;

import java.util.*;

/**
 * Created by johnuiterwyk on 27/09/2014.
 *
 * http://stackoverflow.com/questions/580160/convert-collection-to-list
 * RMIT Information Retrieval 2014 Slides - IR-03-Ranking1.pdf p.66-73
 *
 */
public class Heapify <T extends Comparable<T>> {

    /**
     * takes a collection of items and returns the top X items
     * makes use of items compareTo to determine which item is greater
     * @param items the items to sort
     * @param count the number items to return
     * @return
     */
    public List<T> getTop(Collection<T> items, int count)
    {

        // create list of X  items
        ArrayList<T> minHeap = new ArrayList<T>(count);
        int i = 0;
        for (T item : items)
        {
            // populate the min heap with the first X nodes
            if (i < count) {
                minHeap.add(item);
            } else {
                //if current item is greater than root node
                if (item.compareTo(minHeap.get(0)) == 1) {
                    //replace root node
                    minHeap.set(0, item);
                }
            }

            // sort the list
            heapSort(minHeap);

            i++;
        }

        // sort the min heap array and reverse it to get a highest to lowest list
        Collections.sort(minHeap);
        Collections.reverse(minHeap);

        return minHeap;
    }


    public void heapSort(ArrayList<T> items)
    {
        for (int i = (int)Math.floor((double)(items.size()) / 2.0) ; i >= 0 ; i--)
        {
            heapify (items, i);
        }
    }

    /***
     * heapify sorts an ArrayList of items based on
     * @param items
     * @param position
     */
    private void heapify(ArrayList<T> items, int position)
    {
        int childPosition = 2 * position+1;              // child_pos = left child
        while (childPosition < items.size())
        {
            // if i has two children, check if left or right is smaller
            if(childPosition < items.size()-1 ) {
                T leftChild = items.get(childPosition);
                T rightChild = items.get(childPosition + 1);
                if (leftChild.compareTo(rightChild) == 1) {
                    childPosition++;
                }
            }
            //if parent is smaller or equal to child, then break
            if(items.get(position).compareTo(items.get(childPosition)) <= 0)
            {
                break;
            }else
            {
                Collections.swap(items,position,childPosition);
                position = childPosition;
                childPosition = 2*position+1;
            }


        }
    }
}
