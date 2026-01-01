package test;

import model.Book;
import model.Loan;
import model.Member;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    @Test
    void getLateDays_returnsZero_whenReturnedOnTime() {
        Book b = new Book("B1", "T", "A", "1", 1);
        Member m = new Member("M1", "Ali", "a@a.com");

        LocalDate loanDate = LocalDate.of(2025, 1, 1);
        LocalDate due = loanDate.plusDays(7);

        Loan loan = new Loan("L1", b, m, loanDate, due);
        loan.returnBook(due);

        assertEquals(0, loan.getLateDays());
    }

    @Test
    void getLateDays_returnsPositive_whenLate() {
        Book b = new Book("B1", "T", "A", "1", 1);
        Member m = new Member("M1", "Ali", "a@a.com");

        LocalDate loanDate = LocalDate.of(2025, 1, 1);
        LocalDate due = loanDate.plusDays(7);

        Loan loan = new Loan("L1", b, m, loanDate, due);
        loan.returnBook(due.plusDays(3));

        assertEquals(3, loan.getLateDays());
    }

    @Test
    void returnBook_twice_throws() {
        Book b = new Book("B1", "T", "A", "1", 1);
        Member m = new Member("M1", "Ali", "a@a.com");

        LocalDate loanDate = LocalDate.of(2025, 1, 1);
        LocalDate due = loanDate.plusDays(7);

        Loan loan = new Loan("L1", b, m, loanDate, due);
        loan.returnBook(due);

        assertThrows(IllegalStateException.class, () -> loan.returnBook(due.plusDays(1)));
    }
}

