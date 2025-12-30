package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {

    private String loanId;
    private Book book;
    private Member member;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(String loanId, Book book, Member member, LocalDate loanDate, LocalDate dueDate) {
        if (loanId == null || loanId.isBlank()) {
            throw new IllegalArgumentException("loanId is required");
        }
        if (book == null) {
            throw new IllegalArgumentException("book is required");
        }
        if (member == null) {
            throw new IllegalArgumentException("member is required");
        }
        if (loanDate == null) {
            throw new IllegalArgumentException("loanDate is required");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("dueDate is required");
        }
        if (dueDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("dueDate cannot be before loanDate");
        }

        this.loanId = loanId;
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    public String getLoanId() {
        return loanId;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    // Kitap iadesi
    public void returnBook(LocalDate returnDate) {
        if (returnDate == null) {
            throw new IllegalArgumentException("returnDate is required");
        }
        if (this.returnDate != null) {
            throw new IllegalStateException("Book already returned");
        }
        if (returnDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("returnDate cannot be before loanDate");
        }

        this.returnDate = returnDate;
        book.returnCopy(); // iade ile stok artırma
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    // Gecikme gün sayısını hesaplıyoruz 
    public int getLateDays() {
        if (returnDate == null || !returnDate.isAfter(dueDate)) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(dueDate, returnDate);
    }

    // POLYMORPHISM : ücret member türüne göre değişir (Member vs StudentMember)
    public double calculateLateFee() {
        return member.calculateFee(getLateDays());
    }
}
