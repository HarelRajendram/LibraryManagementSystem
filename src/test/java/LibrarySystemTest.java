import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.yaa.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarySystemTest {

    @Test
    @DisplayName("Valid ISBN should return true")
    void testValidISBN1() {
        assertTrue(Book.isValidISBN("9780306406157"));
    }

    @Test
    @DisplayName("Invalid ISBN should return false")
    void testInvalidISBN1() {
        assertFalse(Book.isValidISBN("1234567890123"));
    }

    @Test
    @DisplayName("Student can borrow a book successfully")
    void testBorrowSuccess() throws Exception {
        Library lib = new Library();
        Book book = new Book("1", "9780306406157", "Test", "Author", Book.Genre.SCIENCE);
        Student student = new Student("S1", "Harel");

        lib.getListItems().add(book);
        lib.getListUser().add(student);
        lib.getItemMap().put("1", book);
        lib.getUserMap().put("S1", student);

        lib.borrowItem("S1", "1");

        assertEquals(Item.ItemStatus.BORROWED, book.getItemStatus());
        assertTrue(student.getBorrowedItems().contains(book));
    }

    @Test
    @DisplayName("Borrowing an already borrowed item should throw exception")
    void testBorrowAlreadyBorrowed() throws Exception {
        Library lib = new Library();
        Book book = new Book("1", "9780306406157", "Test", "Author", Book.Genre.SCIENCE);
        Student s1 = new Student("S1", "A");
        Student s2 = new Student("S2", "B");

        lib.getListItems().add(book);
        lib.getListUser().add(s1);
        lib.getListUser().add(s2);
        lib.getItemMap().put("1", book);
        lib.getUserMap().put("S1", s1);
        lib.getUserMap().put("S2", s2);

        lib.borrowItem("S1", "1");

        assertThrows(LibraryException.class, () -> lib.borrowItem("S2", "1"));
    }

    @Test
    @DisplayName("Student cannot borrow a DVD")
    void testStudentCannotBorrowDVD() {
        Library lib = new Library();
        DVD dvd = new DVD("1", "Movie", "Director", 120);
        Student student = new Student("S1", "Harel");

        lib.getListItems().add(dvd);
        lib.getListUser().add(student);
        lib.getItemMap().put("1", dvd);
        lib.getUserMap().put("S1", student);

        assertThrows(LibraryException.class, () -> lib.borrowItem("S1", "1"));
    }

    @Test
    @DisplayName("Student borrow limit should be enforced")
    void testStudentBorrowLimit() throws Exception {
        Library lib = new Library();
        Student student = new Student("S1", "Harel");

        lib.getListUser().add(student);
        lib.getUserMap().put("S1", student);

        for (int i = 1; i <= 5; i++) {
            Book b = new Book(String.valueOf(i), "9780306406157", "T", "A", Book.Genre.SCIENCE);
            lib.getListItems().add(b);
            lib.getItemMap().put(b.getItemId(), b);
            lib.borrowItem("S1", b.getItemId());
        }

        Book extra = new Book("6", "9780306406157", "X", "Y", Book.Genre.SCIENCE);
        lib.getListItems().add(extra);
        lib.getItemMap().put("6", extra);

        assertThrows(LibraryException.class, () -> lib.borrowItem("S1", "6"));
    }

    @Test
    @DisplayName("Returning a borrowed item should succeed")
    void testReturnSuccess() throws Exception {
        Library lib = new Library();
        Book book = new Book("1", "9780306406157", "Test", "Author", Book.Genre.SCIENCE);
        Student student = new Student("S1", "Harel");

        lib.getListItems().add(book);
        lib.getListUser().add(student);
        lib.getItemMap().put("1", book);
        lib.getUserMap().put("S1", student);

        lib.borrowItem("S1", "1");
        lib.returnItem("S1", "1");

        assertEquals(Item.ItemStatus.IN_STORE, book.getItemStatus());
        assertFalse(student.getBorrowedItems().contains(book));
    }

    @Test
    @DisplayName("Returning an item not borrowed should throw exception")
    void testReturnNotBorrowed() {
        Library lib = new Library();
        Book book = new Book("1", "9780306406157", "Test", "Author", Book.Genre.SCIENCE);
        Student student = new Student("S1", "Harel");

        lib.getListItems().add(book);
        lib.getListUser().add(student);
        lib.getItemMap().put("1", book);
        lib.getUserMap().put("S1", student);

        assertThrows(LibraryException.class, () -> lib.returnItem("S1", "1"));
    }

    @Test
    @DisplayName("Search by title should return matching books")
    void testSearchByTitle() {
        Library lib = new Library();
        Book book = new Book("1", "9780306406157", "Harry Potter", "Rowling", Book.Genre.SCI_FI);
        lib.getListItems().add(book);

        List<Item> results = lib.searchByTitle("Harry Potter");

        assertEquals(1, results.size());
        assertEquals(book, results.get(0));
    }

    @Test
    @DisplayName("Search by author should return matching books")
    void testSearchByAuthor() {
        Library lib = new Library();
        Book book = new Book("1", "9780306406157", "HP", "Rowling", Book.Genre.SCI_FI);
        lib.getListItems().add(book);

        List<Item> results = lib.searchByAuthor("Rowling");

        assertEquals(1, results.size());
        assertEquals(book, results.get(0));
    }

    @Test
    @DisplayName("Recursive search by title should work")
    void testRecursiveSearchByTitle() {
        Library lib = new Library();
        Book book = new Book("1", "9780306406157", "Dune", "Herbert", Book.Genre.SCI_FI);
        lib.getListItems().add(book);

        List<Item> results = lib.searchByTitleRecursion("Dune");

        assertEquals(1, results.size());
        assertEquals(book, results.get(0));
    }

    @Test
    @DisplayName("Stream search by author should work")
    void testStreamSearchByAuthor() {
        Library lib = new Library();
        Book book = new Book("1", "9780306406157", "Dune", "Herbert", Book.Genre.SCI_FI);
        lib.getListItems().add(book);

        List<Item> results = lib.streamSearchAuthor("Herbert");

        assertEquals(1, results.size());
        assertEquals(book, results.get(0));
    }

    @Test
    @DisplayName("Sort items by title")
    void testSortItemsByTitle() {
        Library lib = new Library();
        Book b1 = new Book("1", "9780306406157", "Zebra", "A", Book.Genre.SCIENCE);
        Book b2 = new Book("2", "9780306406157", "Apple", "B", Book.Genre.SCIENCE);

        lib.getListItems().add(b1);
        lib.getListItems().add(b2);

        lib.sortItems(new SortByTitle());

        assertEquals("Apple", ((Book) lib.getListItems().get(0)).getTitle());
    }

    @Test
    @DisplayName("Sort users by name")
    void testSortUsersByName() {
        Library lib = new Library();
        Student s1 = new Student("S1", "Zed");
        Student s2 = new Student("S2", "Adam");

        lib.getListUser().add(s1);
        lib.getListUser().add(s2);
        lib.sortUsers(new SortUserByName());

        assertEquals("Adam", lib.getListUser().get(0).getUserName());
    }
}
