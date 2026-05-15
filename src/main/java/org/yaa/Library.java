package org.yaa;

import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

@Getter
public class Library {
    private List<Item> listItems;
    private List<User> listUser;
    private Map<String, Item> itemMap = new HashMap<>();
    private Map<String, User> userMap = new HashMap<>();


    public Library() {
        this.listItems = new ArrayList<>();
        this.listUser = new ArrayList<>();
    }

    private User findUserById(String userId) {
        return userMap.get(userId);
    }

    public Item findItemById(String itemId) {
        return itemMap.get(itemId);
    }

    public void sortItems(Sorting strategy) {
        listItems.sort(strategy::compare);
    }
    public void sortUsers(Sorting strategy) {
        listUser.sort(strategy::compare);
    }

    /**
     * borrows item to the user
     * @param userId the user who is borrowing
     * @param itemId the item that is being borrowed
     * @throws LibraryException if the user or item does not exist
     */
    public void borrowItem(String userId, String itemId) throws LibraryException {
        Item item = findItemById(itemId);
        if (item == null) throw new LibraryException("Item does not exist");

        User user = findUserById(userId);
        if (user == null) throw new LibraryException("User does not exist");

        user.borrowItem(item);
    }

    /**
     * returns the item
     * @param userId the user who is returning
     * @param itemId the item that is being returned
     * @throws LibraryException if item or user does not exist
     */
    public void returnItem(String userId, String itemId) throws LibraryException {
        Item item = findItemById(itemId);
        if (item == null) throw new LibraryException("Item does not exist");

        User user = findUserById(userId);
        if (user == null) throw new LibraryException("User does not exist");

        user.returnItem(item);
    }

    /**
     * find the title that the user is trying to find
     * @param title the title that we are trying to find
     * @return the found title
     */
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

    /**
     * find the author that the user is trying to find
     * @param author the author that we are trying to find
     * @return the found author
     */
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

    /**
     * uses recursion to find the title
     * @param title the title that is being searched
     * @param index the index to find the title
     * @param results finds the title searched in
     * @return the title that was searched
     */
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

    /**
     * helper method to help find author
     * @param author the author we are searching for
     * @return the found author
     */
    public List<Item> searchByAuthorRecursion(String author) {
        return searchByAuthorRecursiveHelper(author, 0, new ArrayList<>());
    }

    /**
     * uses recursion to find the author
     * @param author the autor that is being searched
     * @param index the index to find the author
     * @param results finds the author searched in
     * @return the author that was searched
     */
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

    /**
     * searches title with stream
     * @param title the title that is being searched
     * @return the searched title
     */
    public List<Item> streamSearchTitle(String title) {
        return listItems.stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .map(book -> (Item) book)
                .toList();
    }

    /**
     * searches author with stream
     * @param author the author that is being searched
     * @return the searched author
     */
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

    /**
     * a backup file to add the information of the user
     * @throws LibraryException if the backup failed to work
     */
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

    /**
     * a backup file to add the information of the item
     * @throws LibraryException if the backup failed to work
     */
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

    /**
     * loads the items details in a csv file
     * @param filePath the file where the details are going to added to
     * @throws LibraryException if there is no file to add to
     */
    public void loadItemsCSVFiles(String filePath) throws LibraryException {
        try (Scanner scanner = new Scanner(new File(filePath))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                String type = parts[0].trim();
                String id = parts[1].trim();
                String isbnOrTitle = parts[2].trim();
                String titleOrDirector = parts[3].trim();
                String authorOrDuration = parts[4].trim();
                String genreOrPublisher = parts[5].trim();
                Item.ItemStatus status = Item.ItemStatus.valueOf(parts[6].trim());

                Item item;

                switch (type) {

                    case "Book":
                        item = new Book(
                                id, isbnOrTitle, titleOrDirector, authorOrDuration,
                                Book.Genre.valueOf(genreOrPublisher)
                        );
                        break;

                    case "DVD":
                        item = new DVD( id, isbnOrTitle, titleOrDirector,
                                Integer.parseInt(authorOrDuration)
                        );
                        break;

                    case "Magazine":
                        item = new Magazine(id, isbnOrTitle,
                                Integer.parseInt(titleOrDirector),
                                authorOrDuration
                        );
                        break;

                    default:
                        continue;
                }
                item.setItemStatus(status);
                listItems.add(item);
                itemMap.put(item.getItemId(), item);
            }

        } catch (Exception e) {
            throw new LibraryException("Failed to load items CSV");
        }
    }

    /**
     * loads the users details in a csv file
     * @param filePath the file where the details are going to added to
     * @throws LibraryException if there is no file to add to
     */
    public void loadUsersCSVFiles(String filePath) throws LibraryException {
        try(Scanner scanner = new Scanner(new File(filePath))) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                String id = parts[0].trim();
                String name = parts[1].trim();
                String type = parts[2].trim();
                String borrowedItems = parts.length > 3 ? parts[3] : "";
                User user;

                switch (type) {
                    case "Student":
                        user = new Student(id, name);
                        break;

                    case "Teacher":
                        user = new Teacher(id, name);
                        break;

                    case "Admin":
                        user = new Admin(id, name);
                        break;

                    default:
                        continue;
                }
                listUser.add(user);
                userMap.put(user.getUserId(), user);

                if (!(borrowedItems.isEmpty())) {
                    for (String itemId : borrowedItems.split(";")) {
                        Item item = findItemById(itemId);
                        if (item != null) {
                            item.setItemStatus(Item.ItemStatus.BORROWED);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new LibraryException("Fsiled to load items CSV");
        }
    }
}
