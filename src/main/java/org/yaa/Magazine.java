package org.yaa;

public class Magazine extends Item {
    private String title;
    private String issueNumber;
    private String publisher;


    public Magazine(String title, String issueNumber, String publisher) {
        super();
        this.title = title;
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }
}
