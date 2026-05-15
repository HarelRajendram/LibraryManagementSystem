package org.yaa;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        loadSampleData(library);

        while (true) {
            System.out.println("\n===== LIBRARY SYSTEM MENU =====");
            System.out.println("1. Search");
            System.out.println("2. Borrow Item");
            System.out.println("3. Return Item");
            System.out.println("4. Mark Item as LOST");
            System.out.println("5. Admin Menu");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> searchMenu(library, scanner);
                case "2" -> borrowMenu(library, scanner);
                case "3" -> returnMenu(library, scanner);
                case "4" -> markLostMenu(library, scanner);
                case "5" -> adminMenu(library, scanner);
                case "6" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // Here is the search menu for the system

    private static void searchMenu(Library library, Scanner scanner) {
        System.out.println("\n===== SEARCH MENU =====");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Title (Recursive)");
        System.out.println("3. Search by Author");
        System.out.println("4. Search by Author (Recursive)");
        System.out.println("5. Back");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        if (choice.equals("5")) return;

        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        switch (choice) {
            case "1" -> System.out.println(library.searchByTitle(keyword));
            case "2" -> System.out.println(library.searchByTitleRecursion(keyword));
            case "3" -> System.out.println(library.searchByAuthor(keyword));
            case "4" -> System.out.println(library.searchByAuthorRecursion(keyword));
            default -> System.out.println("Invalid option.");
        }
    }

    //This is the borrow menu for the library
    private static void borrowMenu(Library library, Scanner scanner) {
        try {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter Item ID: ");
            String itemId = scanner.nextLine();

            library.borrowItem(userId, itemId);
            System.out.println("Item borrowed successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // This is the return menu for the library
    private static void returnMenu(Library library, Scanner scanner) {
        try {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter Item ID: ");
            String itemId = scanner.nextLine();

            library.returnItem(userId, itemId);
            System.out.println("Item returned successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // This methods marks the item status of the book to LOST
    private static void markLostMenu(Library library, Scanner scanner) {
        System.out.print("Enter Item ID to mark LOST: ");
        String itemId = scanner.nextLine();

        Item item = library.findItemById(itemId);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        item.setItemStatus(Item.ItemStatus.LOST);
        System.out.println("Item marked as LOST.");
    }

    // this is the menu for the admin users
    private static void adminMenu(Library library, Scanner scanner) {
        System.out.println("\n===== ADMIN MENU =====");
        System.out.println("1. Generate Report");
        System.out.println("2. Add New Book");
        System.out.println("3. Backup Data");
        System.out.println("4. Back");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> generateReport(library);
            case "2" -> addBookMenu(library, scanner);
            case "3" -> {
                try {
                    library.backupData();
                    System.out.println("Backup completed successfully.");
                } catch (Exception e) {
                    System.out.println("Backup failed: " + e.getMessage());
                }
            }
            default -> System.out.println("Returning to main menu.");
        }
    }

    // this will create a report which only the admins can only access
    private static void generateReport(Library library) {
        System.out.println("\n===== ADMIN REPORT =====");

        System.out.println("\nBorrowed Items:");
        library.getListItems().stream()
                .filter(i -> i.getItemStatus() == Item.ItemStatus.BORROWED)
                .forEach(System.out::println);

        System.out.println("\nIn-Store Items:");
        library.getListItems().stream()
                .filter(i -> i.getItemStatus() == Item.ItemStatus.IN_STORE)
                .forEach(System.out::println);

        System.out.println("\nLost Items:");
        library.getListItems().stream()
                .filter(i -> i.getItemStatus() == Item.ItemStatus.LOST)
                .forEach(System.out::println);
    }


    // This method will add the book
    private static void addBookMenu(Library library, Scanner scanner) {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter Genre (SCIENCE, SCI_FI, HISTORY, FANTASY): ");
        String genre = scanner.nextLine();

        Book book = new Book(id, isbn, title, author, Book.Genre.valueOf(genre));
        library.getListItems().add(book);
        library.getItemMap().put(id, book);

        System.out.println("Book added successfully. New ID: " + id);
    }

    // this is the sample data for the library
    private static void loadSampleData(Library library) {
        Book hobbit = new Book("101", "9780547928227", "The Hobbit", "Tolkien", Book.Genre.FANTASY);
        Book atomic = new Book("103", "9780735211292", "Atomic Habits", "James Clear", Book.Genre.SCIENCE);
        Book clean = new Book("104", "9780132350884", "Clean Code", "Robert Martin", Book.Genre.SCIENCE);
        Book dune = new Book("105", "9780441172719", "Dune", "Frank Herbert", Book.Genre.SCI_FI);

        DVD interstellar = new DVD("102", "Interstellar", "Christopher Nolan", 169);

        Student alice = new Student("S1", "Alice");
        Teacher bob = new Teacher("T1", "Bob");

        library.getListItems().add(hobbit);
        library.getListItems().add(interstellar);
        library.getListItems().add(atomic);
        library.getListItems().add(clean);
        library.getListItems().add(dune);

        library.getListUser().add(alice);
        library.getListUser().add(bob);

        library.getItemMap().put("101", hobbit);
        library.getItemMap().put("102", interstellar);
        library.getItemMap().put("103", atomic);
        library.getItemMap().put("104", clean);
        library.getItemMap().put("105", dune);

        library.getUserMap().put("S1", alice);
        library.getUserMap().put("T1", bob);
    }
}
