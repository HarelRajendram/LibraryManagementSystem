package org.yaa;

public class SortByStatus implements Sorting {

    @Override
    public int compare(Object o1, Object o2) {
        Item i1 = (Item) o1;
        Item i2 = (Item) o2;

        return i1.getItemStatus().compareTo(i2.getItemStatus());
    }
}
