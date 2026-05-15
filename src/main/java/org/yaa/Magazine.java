package org.yaa;

import lombok.Getter;

@Getter
public class Magazine extends Item {

    private String title;
    private int issueNumber;
    private String publisher;

    // CSV loading constructor
    public Magazine(String itemId, String title, int issueNumber, String publisher) {
        super(itemId);
        this.title = title;
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    // Creating new magazines
    public Magazine(String title, int issueNumber, String publisher) {
        super();
        this.title = title;
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }
}
