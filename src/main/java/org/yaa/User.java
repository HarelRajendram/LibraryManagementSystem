package org.yaa;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String userId;
    protected String userName;
    protected List<Item> borrowedItems;

    public abstract int getBorrowLimit();

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.borrowedItems = new ArrayList<>();
    }

    public void borrowItem(Item item) throws LibraryException {
        if (borrowedItems.size() >= getBorrowLimit()) {
            throw new LibraryException("book limit reached");
        }
        if (item.getItemStatus() == Item.ItemStatus.BORROWED) {
            throw new LibraryException("book is already borrowed");
        }
        borrowedItems.add(item);
        item.setItemStatus(Item.ItemStatus.BORROWED);
    }
    public void returnItem(Item item) throws LibraryException {
        if (!(borrowedItems.contains(item))) {
            throw new LibraryException("user did not borrow the item");
        }
        if (item.getItemStatus() == Item.ItemStatus.IN_STORE) {
            throw new LibraryException("book cannot be borrowed");
        }
        borrowedItems.remove(item);
        item.setItemStatus(Item.ItemStatus.IN_STORE);
    }
}
