package org.yaa;

public class SortByAuthor implements Sorting {
    @Override
    public int compare(Item i1, Item i2) {
        String author1 = "";
        String author2 = "";
        if (i1 instanceof Book book) {
            author1 = book.getAuthor();
        }
        if (i2 instanceof Book book) {
            author2 = book.getAuthor();
        }
        return author1.compareToIgnoreCase(author2);
    }
}
