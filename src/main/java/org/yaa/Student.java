package org.yaa;


public class Student extends User {

    public Student(String userId, String userName) {
        super(userId, userName);
    }

    @Override
    public void borrowItem(Item item) throws LibraryException {
        if (!(item instanceof Book book)) {
            throw new LibraryException("students can only borrow books");
        }
        super.borrowItem(item);
    }

    public int getBorrowLimit() {
        return 5;
    }
}
