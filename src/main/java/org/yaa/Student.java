package org.yaa;

import java.util.List;

public class Student extends User {

    public Student(String userId, String userName, List<Item> borrowedItems) {
        super(userId, userName);
    }
    public int getBorrowLimit() {
        return 5;
    }
}
