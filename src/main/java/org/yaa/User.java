package org.yaa;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
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

    /**
     * borrows the item and adds it to the users borrowed list
     * @param item the item that the user is going to borrow
     * @throws LibraryException if the item is already borrowed
     */
    public void borrowItem(Item item) throws LibraryException {
        if (borrowedItems.size() >= getBorrowLimit()) {
            throw new LibraryException("Borrow limit reached");
        }
        if (item.getItemStatus() == Item.ItemStatus.BORROWED) {
            throw new LibraryException("Item already borrowed");
        }
        borrowedItems.add(item);
        item.setItemStatus(Item.ItemStatus.BORROWED);
    }

    /**
     * return the item in the users borrowed list
     * @param item the item that the user is returning
     * @throws LibraryException if the item is already in store
     */
    public void returnItem(Item item) throws LibraryException {
        if (!borrowedItems.contains(item)) {
            throw new LibraryException("User did not borrow this item");
        }
        borrowedItems.remove(item);
        item.setItemStatus(Item.ItemStatus.IN_STORE);
    }
}
