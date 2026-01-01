package test;

import model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    void constructor_validatesRequiredFields() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("", "Title", "A", "1", 1));

        assertThrows(IllegalArgumentException.class,
                () -> new Book("B1", "", "A", "1", 1));

        assertThrows(IllegalArgumentException.class,
                () -> new Book("B1", "Title", "A", "1", 0));
    }

    @Test
    void borrowCopy_decreasesAvailableUntilZero() {
        Book b = new Book("B1", "T", "A", "1", 1);

        assertTrue(b.borrowCopy());
        assertEquals(0, b.getAvailableCopies());
        assertFalse(b.borrowCopy()); // stok bitti
    }

    @Test
    void returnCopy_doesNotExceedTotal() {
        Book b = new Book("B1", "T", "A", "1", 1);

        assertTrue(b.borrowCopy());
        b.returnCopy();
        assertEquals(1, b.getAvailableCopies());

        b.returnCopy(); 
        assertEquals(1, b.getAvailableCopies());
    }
}

