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
    public Loan(String loanId, Book book, Member member,
            LocalDate loanDate, LocalDate dueDate) {

    if (loanId == null || loanId.isBlank()) {
        throw new IllegalArgumentException("loanId is required");
    }
    if (book == null || member == null) {
        throw new IllegalArgumentException("book and member are required");
    }
    if (dueDate.isBefore(loanDate)) {
        throw new IllegalArgumentException("dueDate cannot be before loanDate");
    }

    this.loanId = loanId;
    this.book = book;
    this.member = member;
    this.loanDate = loanDate;
    this.dueDate = dueDate;
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

    public void returnBook(LocalDate returnDate) {
        if (this.returnDate != null) {
            throw new IllegalStateException("Book already returned");
        }
        this.returnDate = returnDate;
        book.returnCopy();
    }


    public boolean isReturned() {
        return returnDate != null;
    }

    public int getLateDays() {
        if (returnDate == null || !returnDate.isAfter(dueDate)) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(dueDate, returnDate);
    }
}