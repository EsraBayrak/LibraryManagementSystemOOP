package service;

import model.Book;
import model.Loan;
import model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {

    private final List<Book> books;
    private final List<Member> members;
    private final List<Loan> loans;

    public LibraryManager() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    
    public List<Book> getBooks() { return books; }
    public List<Member> getMembers() { return members; }
    public List<Loan> getLoans() { return loans; }

  
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book required");
        }
        if (findBookById(book.getId()) != null) {
            throw new IllegalArgumentException("Book ID already exists: " + book.getId());
        }
        books.add(book);
    }

    public Book findBookById(String id) {
        if (id == null) return null;

        for (Book b : books) {
        	if (b.getId().equalsIgnoreCase(id)) return b;
        }
        return null;
    }

 
    public boolean removeBook(String bookId) {
        Book b = findBookById(bookId);
        if (b == null) {
            return false;
        }

       
        if (b.getAvailableCopies() < b.getTotalCopies()) {
            return false;
        }

        return books.remove(b);
    }

    
    public void addMember(Member member) {
        if (member == null)  {
            throw new IllegalArgumentException("member required");
        }
        if (findMemberById(member.getMemberId()) != null) {
            throw new IllegalArgumentException("Member ID already exists: " + member.getMemberId());
        }
        members.add(member);
    }

    public Member findMemberById(String memberId) {
        if (memberId == null) return null;

        for (Member m : members) {
        	  if (m.getMemberId().equalsIgnoreCase(memberId)) return m;
        }
        return null;
    }

    
    public Loan findLoanById(String loanId) {
        if (loanId == null) return null;

        for (Loan l : loans) {
            if (loanId.equals(l.getLoanId())) {
                return l;
            }
        }
        return null;
    }

    public boolean borrowBook(String loanId, String bookId, String memberId,
                              LocalDate loanDate, LocalDate dueDate) {

        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found: " + bookId);
            return false;
        }

        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found: " + memberId);
            return false;
        }

        if (!book.borrowCopy()) {
            System.out.println("No available copies for book: " + bookId);
            return false;
        }

        try {
            Loan loan = new Loan(loanId, book, member, loanDate, dueDate);
            loans.add(loan);
            return true;
        } catch (Exception e) {
            
            book.returnCopy();
            System.out.println("Loan create failed: " + e.getMessage());
            return false;
        }
    }

    public boolean returnBook(String loanId) {
        Loan loan = findLoanById(loanId);
        if (loan == null) {
            System.out.println("Loan not found: " + loanId);
            return false;
        }
        if (loan.isReturned()) {
            System.out.println("Loan already returned: " + loanId);
            return false;
        }

        try {
            loan.returnBook(LocalDate.now());
            return true;
        } catch (Exception e) {
            System.out.println("Return failed: " + e.getMessage());
            return false;
        }
    }

    
    public List<Book> searchBooks(String query) {
        List<Book> out = new ArrayList<>();
        if (query == null || query.isBlank()) return out;

        for (Book b : books) {
            if (b.matches(query)) {
                out.add(b);
            }
        }
        return out;
    }

    public List<Member> searchMembers(String query) {
        List<Member> out = new ArrayList<>();
        if (query == null || query.isBlank()) return out;

        for (Member m : members) {
            if (m.matches(query)) {
                out.add(m);
            }
        }
        return out;
    }
}

