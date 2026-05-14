package org.yaa;

import lombok.Getter;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Library {

    @Getter
    private List<Item> listItems;

    @Getter
    private List<User> listUser;

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

    public void sortItems(Sorting strategy) {
        listItems.sort(strategy::compare);
    }
    public void sortUsers(Sorting strategy) {
        listUser.sort(strategy::compare);
    }

    public void borrowItem(String userId, String itemId) throws LibraryException {
        Item item = findItemById(itemId);
        if (item == null) throw new LibraryException("Item does not exist");

        User user = findUserById(userId);
        if (user == null) throw new LibraryException("User does not exist");

        user.borrowItem(item);
    }

    public void returnItem(String userId, String itemId) throws LibraryException {
        Item item = findItemById(itemId);
        if (item == null) throw new LibraryException("Item does not exist");

        User user = findUserById(userId);
        if (user == null) throw new LibraryException("User does not exist");

        user.returnItem(item);
    }

    public List<Item> searchByTitle(String title) {
        List<Item> results = new ArrayList<>();
        for (Item item : listItems) {
            if (item instanceof Book book) {
                if (book.getTitle().equalsIgnoreCase(title)) {
                    results.add(item);
                }
            }
        }
        return results;
    }

    public List<Item> searchByAuthor(String author) {
        List<Item> results = new ArrayList<>();
        for (Item item : listItems) {
            if (item instanceof Book book) {
                if (book.getAuthor().equalsIgnoreCase(author)) {
                    results.add(item);
                }
            }
        }
        return results;
    }

    public List<Item> searchByTitleRecursion(String title) {
        return searchByTitleRecursiveHelper(title, 0, new ArrayList<>());
    }

    private List<Item> searchByTitleRecursiveHelper(String title, int index, List<Item> results) {
        if (index >= listItems.size()) return results;

        Item item = listItems.get(index);
        if (item instanceof Book book) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                results.add(item);
            }
        }
        return searchByTitleRecursiveHelper(title, index + 1, results);
    }

    public List<Item> searchByAuthorRecursion(String author) {
        return searchByAuthorRecursiveHelper(author, 0, new ArrayList<>());
    }

    private List<Item> searchByAuthorRecursiveHelper(String author, int index, List<Item> results) {
        if (index >= listItems.size()) return results;

        Item item = listItems.get(index);
        if (item instanceof Book book) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                results.add(item);
            }
        }
        return searchByAuthorRecursiveHelper(author, index + 1, results);
    }

    public List<Item> streamSearchTitle(String title) {
        return listItems.stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .map(book -> (Item) book)
                .toList();
    }

    public List<Item> streamSearchAuthor(String author) {
        return listItems.stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .map(book -> (Item) book)
                .toList();
    }

    public void backupData() throws LibraryException {
        backupUsers();
        backupItems();
    }

    private void backupUsers() throws LibraryException {
        try (FileWriter writer = new FileWriter(Constants.USERS_BACKUP_PATH)) {

            for (User user : listUser) {
                String userType = user.getClass().getSimpleName();

                List<String> borrowedIds = new ArrayList<>();
                for (Item item : user.getBorrowedItems()) {
                    borrowedIds.add(item.getItemId());
                }

                writer.write(user.getUserId() + "," +
                        user.getUserName() + "," +
                        userType + "," +
                        String.join(";", borrowedIds) + "\n");
            }

        } catch (Exception e) {
            throw new LibraryException("Failed to backup users");
        }
    }

    private void backupItems() throws LibraryException {
        try (FileWriter writer = new FileWriter(Constants.ITEMS_BACKUP_PATH)) {

            for (Item item : listItems) {

                if (item instanceof Book book) {
                    writer.write(book.getItemId() + "," +
                            book.getTitle() + "," +
                            book.getAuthor() + "," +
                            book.getGenre() + "," +
                            book.getItemStatus() + "\n");

                } else if (item instanceof DVD dvd) {
                    writer.write(dvd.getItemId() + "," +
                            dvd.getTitle() + "," +
                            dvd.getDVDDirector() + "," +
                            dvd.getDVDDuration() + "," +
                            dvd.getItemStatus() + "\n");

                } else if (item instanceof Magazine mag) {
                    writer.write(mag.getItemId() + "," +
                            mag.getTitle() + "," +
                            mag.getPublisher() + "," +
                            mag.getIssueNumber() + "," +
                            mag.getItemStatus() + "\n");
                }
            }

        } catch (Exception e) {
            throw new LibraryException("Failed to backup items");
        }
    }
}
