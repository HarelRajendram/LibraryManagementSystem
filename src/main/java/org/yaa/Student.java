package org.yaa;


public class Student extends User {

    public Student(String userId, String userName) {
        super(userId, userName);
    }
    public int getBorrowLimit() {
        return 5;
    }
}
