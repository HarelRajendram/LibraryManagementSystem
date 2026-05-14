package org.yaa;

public class SortUserByBorrowCount implements Sorting {
    @Override
    public int compare(Object o1, Object o2) {
        User u1 = (User) o1;
        User u2 = (User) o2;

        return Integer.compare(
                u1.getBorrowedItems().size(),
                u2.getBorrowedItems().size()
        );
    }
}
