package org.yaa;

public class SortByAuthor implements Sorting {
    @Override
    public int compare(Object o1, Object o2) {
        String author1 = "";
        String author2 = "";
        if (o1 instanceof Book book) {
            author1 = book.getAuthor();
        }
        if (o2 instanceof Book book) {
            author2 = book.getAuthor();
        }
        return author1.compareToIgnoreCase(author2);
    }
}
