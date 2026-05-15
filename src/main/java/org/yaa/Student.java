package org.yaa;


public class Student extends User {

    public Student(String userId, String userName) {
        super(userId, userName);
    }

    /**
     * allows student to borrow items
     * @param item the item that the user is going to borrow
     * @throws LibraryException if the student is borrowing an item other than a book
     */
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
