package org.yaa;

public class SortByTitle implements Sorting {
    @Override
    public int compare(Object o1, Object o2) {
        String title1 = "";
        String title2 = "";
        if (o1 instanceof Book book) {
            title1 = book.getTitle();
        }
        if (o2 instanceof Book book) {
            title2 =  book.getTitle();
        }
        return title1.compareToIgnoreCase(title2);
    }
}
