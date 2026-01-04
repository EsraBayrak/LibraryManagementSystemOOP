package test;

import model.Book;
import model.Loan;
import model.Member;
import model.StudentMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LibraryManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryManagerTest {

    private LibraryManager manager;

    @BeforeEach
    void setup() {
        manager = new LibraryManager();
        manager.addBook(new Book("B1", "Clean Code", "Robert", "1111", 2));
        manager.addBook(new Book("B2", "Effective Java", "Bloch", "2222", 1));

        manager.addMember(new Member("M1", "Ali", "ali@example.com"));
        manager.addMember(new StudentMember("S1", "Esra", "esra@example.com", "CENG"));
    }

    
    @Test
    void addBook_throws_whenDuplicateId() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addBook(new Book("B1", "Another", "X", "9999", 1));
        });
    }

    
    
    @Test
    void borrowBook_success_decreasesAvailableCopies_andCreatesLoan() {
        LocalDate loanDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = loanDate.plusDays(7);

        boolean ok = manager.borrowBook("L1", "B2", "M1", loanDate, dueDate);

        assertTrue(ok);
        assertNotNull(manager.findLoanById("L1"));

        Book b2 = manager.findBookById("B2");
        assertNotNull(b2);
        assertEquals(0, b2.getAvailableCopies()); 
    }

    @Test
    void borrowBook_fails_whenNoStock() {
        LocalDate loanDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = loanDate.plusDays(7);

        assertTrue(manager.borrowBook("L1", "B2", "M1", loanDate, dueDate));
        assertFalse(manager.borrowBook("L2", "B2", "S1", loanDate, dueDate)); 

        Book b2 = manager.findBookById("B2");
        assertEquals(0, b2.getAvailableCopies());
        assertNull(manager.findLoanById("L2")); 
    }

    @Test
    void returnBook_success_marksReturned_andIncreasesStock() {
        LocalDate loanDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = loanDate.plusDays(7);

        assertTrue(manager.borrowBook("L1", "B1", "M1", loanDate, dueDate));
        Book b1 = manager.findBookById("B1");
        assertEquals(1, b1.getAvailableCopies()); 

        boolean ok = manager.returnBook("L1");
        assertTrue(ok);

        Loan loan = manager.findLoanById("L1");
        assertNotNull(loan);
        assertTrue(loan.isReturned());

        assertEquals(2, b1.getAvailableCopies()); 
    }

    @Test
    void returnBook_fails_whenLoanNotFound() {
        assertFalse(manager.returnBook("NO_SUCH_LOAN"));
    }

    @Test
    void searchBooks_returnsMatches() {
        List<Book> res = manager.searchBooks("clean");
        assertEquals(1, res.size());
        assertEquals("B1", res.get(0).getId());
    }

    @Test
    void addMember_throws_whenDuplicateId() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addMember(new Member("M1", "Veli", "veli@example.com"));
        });
    }

    
    @Test
    void addStudentMember_throws_whenDuplicateId() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addMember(new StudentMember("S1", "X", "x@x.com", "CENG"));
        });
    }

    
    
    @Test
    void searchMembers_usesMatches_polymorphismWorks() {
        
        List<Member> res = manager.searchMembers("ceng");
        assertEquals(1, res.size());
        assertEquals("S1", res.get(0).getMemberId());
    }
}


