package org.yaa;

import lombok.Getter;

@Getter
public class Book extends Item {

    private String ISBN;
    private String title;
    private String author;
    private Genre genre;

    public Book(String itemId, String ISBN, String title, String author, Genre genre) {
        super(itemId);
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String ISBN, String title, String author, Genre genre) {
        super();
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public static boolean isValidISBN(String ISBN) {
        if (ISBN.length() != 13) return false;

        int sum = 0;
        int digitIdx = 0;

        for (int i = 0; i < ISBN.length(); i++) {
            char c = ISBN.charAt(i);

            if (c == '-') continue;
            if (!Character.isDigit(c)) return false;

            int digit = c - '0';

            if (digitIdx % 2 == 0) sum += digit * 3;
            else sum += digit;

            digitIdx++;
        }
        return sum % 10 == 0;
    }
    public enum Genre {
        HISTORY, HORROR, SCI_FI, ROMANCE, SCIENCE, MYSTERY, THRILLER
    }
}
