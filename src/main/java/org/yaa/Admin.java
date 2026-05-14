package org.yaa;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User implements Reportable {

    public Admin(String userId, String userName) {
        super(userId, userName);
    }

    @Override
    public int getBorrowLimit() {
        return Integer.MAX_VALUE;
    }
    public void backupData(Library library) throws LibraryException {
        try(FileWriter writer = new FileWriter(Constants.USERS_BACKUP_PATH)) {
            for (User user : library.getListUser()) {
                String userType = user.getClass().getSimpleName();

                List<String> borrowedIds = new ArrayList<>();
                for (Item item : user.borrowedItems) {
                    borrowedIds.add(item.getItemId());
                }
                String borrowedIdsString = String.join(";", borrowedIds);

                writer.write(user.getUserId() + "," +
                        user.getUserName() + "," +
                        userType + "," +
                        borrowedIdsString + "\n");
            }
        } catch (Exception e) {
            throw new LibraryException("Failed to backup users");
        }
        try(FileWriter writer = new FileWriter(Constants.ITEMS_BACKUP_PATH)) {
            for (Item item : library.getListItems()) {
                String itemType = item.getClass().getSimpleName();

                if (item instanceof Book book) {
                    writer.write(book.getItemId() + "," +
                            book.getTitle() + "," +
                            book.getAuthor() + "," +
                            book.getGenre() + "," +
                            book.getItemStatus() + "\n"
                            );
                } else if (item instanceof  DVD dvd) {
                    writer.write(dvd.getItemId() + "," +
                            dvd.getTitle() + "," +
                            dvd.getDVDDirector() + "," +
                            dvd.getDVDDuration() + "," +
                            dvd.getItemStatus() + "\n"
                    );
                } else if (item instanceof Magazine mag) {
                    writer.write(mag.getItemId() + "," +
                            mag.getTitle() + "," +
                            mag.getPublisher() + "," +
                            mag.getIssueNumber() + "," +
                            mag.getItemStatus() + "\n"
                    );
                }
            }
        } catch (Exception e) {
            throw new LibraryException("Failed to backup Items");
        }
    }
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Admin report").append(userName).append("\n");
        sb.append("Borrowed Items: \n");

        for (Item item : borrowedItems) {
            sb.append("-").append(item.getItemId())
                    .append("(").append(item.getItemStatus())
                    .append(")");
        }
        return sb.toString();
    }
}
