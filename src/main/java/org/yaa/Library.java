package org.yaa;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Library {
    @Getter
    private List<Item> listItems;
    @Getter private List<User> listUser;

    public Library() {
        this.listItems = new ArrayList<>();
        this.listUser = new ArrayList<>();
    }

    private User findUserById(String userId) {
        for (User user : listUser) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    public Item findItemById(String itemId) {
        for (Item item : listItems) {
            if (item.getItemId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }
    public void borrowItem(String userId, String itemId) throws LibraryException {
        Item item = findItemById(itemId);
        if (item == null) {
            throw new LibraryException("item does not exist");
        }
        User user = findUserById(userId);
        if (user == null) {
            throw new LibraryException("user does not exist");
        }
        user.borrowItem(item);
    }
    public void returnItem(String userId, String itemId) throws LibraryException {
        Item item = findItemById(itemId);
        if (item == null) {
            throw new LibraryException("item does not exist");
        }
        User user = findUserById(userId);
        if (user == null) {
            throw new LibraryException("user does not exist");
        }
        user.returnItem(item);
    }
}
