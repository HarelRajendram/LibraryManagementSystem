package org.yaa;

public class Teacher extends User {

    public Teacher(String userId, String userName) {
        super(userId, userName);
    }
    public int getBorrowLimit() {
        return 10;
    }
}
