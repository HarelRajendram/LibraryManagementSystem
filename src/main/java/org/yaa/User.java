package org.yaa;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String userId;
    protected String userName;
    protected List<Item> borrowedItems;

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.borrowedItems = new ArrayList<>();
    }

    public void borrowItem(Item item) {
        if (!borrowedItems.contains(item)) {
            borrowedItems.add(item);
        }
    }
    public void returnItem(Item item) {
        if (!borrowedItems.contains(item)) {
            borrowedItems.remove(item);
        }
    }
}
