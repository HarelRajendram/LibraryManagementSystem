package org.yaa;

public class SortByStatus implements Sorting {
    @Override
    public int compare(Item i1, Item i2) {
        return i1.getItemStatus().compareTo(i2.getItemStatus());
    }
}
