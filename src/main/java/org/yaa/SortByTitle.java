package org.yaa;

public class SortByTitle implements Sorting {
    @Override
    public int compare(Item i1, Item i2) {
        String title1 = "";
        String title2 = "";
        if (i1 instanceof Book book) {
            title1 = book.getTitle();
        }
        if (i2 instanceof Book book) {
            title2 =  book.getTitle();
        }
        return title1.compareToIgnoreCase(title2);
    }
}
