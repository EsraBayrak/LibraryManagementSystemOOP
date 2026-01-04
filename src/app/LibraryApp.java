package app;

import model.Book;
import model.Loan;
import model.Member;
import model.StudentMember;
import service.LibraryManager;

import java.time.LocalDate;
import java.util.Scanner;

public class LibraryApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();

        seedDemoData(manager); 

        System.out.println("=== Library Management System ===");
        listBooksFlow(manager);
        listMembersFlow(manager);
        
        while (true) {
            printMenu();
            int choice = readInt("Choice: ");

            switch (choice) {
                case 1 -> addBookFlow(manager);
                case 2 -> removeBookFlow(manager);
                case 3 -> listBooksFlow(manager);
                case 4 -> addMemberFlow(manager);
                case 5 -> listMembersFlow(manager);
                case 6 -> borrowBookFlow(manager);
                case 7 -> returnBookFlow(manager);
                case 8 -> listLoansFlow(manager);
                case 9 -> searchBooksFlow(manager);
                case 10 -> searchMembersFlow(manager);
                case 11 -> {
                    System.out.println("Exiting the system.");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter 1-11.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1) Add book");
        System.out.println("2) Remove book");
        System.out.println("3) List books");
        System.out.println("4) Add member");
        System.out.println("5) List members");
        System.out.println("6) Borrow book");
        System.out.println("7) Return book");
        System.out.println("8) List loans");
        System.out.println("9) Search books");
        System.out.println("10) Search members");
        System.out.println("11) Exit");
    }

  

    private static void addBookFlow(LibraryManager manager) {
        System.out.println("\n--- ADD BOOK ---");
        String id = readLine("Book ID: ");
        String title = readLine("Title: ");
        String author = readLine("Author: ");
        String isbn = readLine("ISBN: ");
        int totalCopies = readInt("Total copies: ");

        try {
            Book book = new Book(id, title, author, isbn, totalCopies);
            manager.addBook(book);
            System.out.println("Book added: " + id + " - " + title);
        } catch (Exception e) {
            System.out.println("Failed to add book: " + e.getMessage());
        }
    }

    private static void removeBookFlow(LibraryManager manager) {
        System.out.println("\n--- REMOVE BOOK ---");
        String bookId = readLine("Book ID: ");

        boolean ok = manager.removeBook(bookId);
        if (ok) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Remove failed. Book not found or currently loaned out.");
        }
    }

    private static void listBooksFlow(LibraryManager manager) {
        System.out.println("\n--- BOOK LIST ---");
        if (manager.getBooks().isEmpty()) {
            System.out.println("No books in library.");
            return;
        }

        for (Book b : manager.getBooks()) {
            System.out.println(b.getId() + " - " + b.getTitle()
                    + " | available: " + b.getAvailableCopies() + "/" + b.getTotalCopies());
        }
    }

    private static void addMemberFlow(LibraryManager manager) {
        System.out.println("\n--- ADD MEMBER ---");
        System.out.println("1) Normal Member");
        System.out.println("2) Student Member");
        int type = readInt("Type: ");

        String memberId = readLine("Member ID: ");
        String name = readLine("Name: ");
        String email = readLine("Email: ");

        try {
            if (type == 2) {
                String dept = readLine("Department: ");
                manager.addMember(new StudentMember(memberId, name, email, dept));
            } else {
                manager.addMember(new Member(memberId, name, email));
            }
            System.out.println("Member added: " + memberId + " - " + name);
        } catch (Exception e) {
            System.out.println("Failed to add member: " + e.getMessage());
        }
    }

    private static void listMembersFlow(LibraryManager manager) {
        System.out.println("\n--- MEMBER LIST ---");
        if (manager.getMembers().isEmpty()) {
            System.out.println("No members.");
            return;
        }

       
        for (Member m : manager.getMembers()) {
            String type = (m instanceof StudentMember) ? "STUDENT" : "NORMAL";

            String extra = "";
            if (m instanceof StudentMember sm) {
                extra = " | dept: " + sm.getDepartment();
            }

            System.out.println(type + " | " + m.getMemberId()
                    + " - " + m.getName()
                    + " | " + m.getEmail()
                    + extra);
        }
    }

    private static void borrowBookFlow(LibraryManager manager) {
        System.out.println("\n--- BORROW BOOK ---");
        String loanId = readLine("Loan ID: ");
        String bookId = readLine("Book ID: ");
        String memberId = readLine("Member ID: ");

        int loanDays = readInt("How many days?: ");
        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanDate.plusDays(loanDays);

        boolean ok = manager.borrowBook(loanId, bookId, memberId, loanDate, dueDate);
        if (ok) {
            System.out.println("Borrowed successfully.");
            
            Book b = manager.findBookById(bookId);
            if (b != null) {
                System.out.println("Availability updated: " 
                        + b.getAvailableCopies() + "/" + b.getTotalCopies());}
        } else {
            System.out.println("Borrow failed.");
        }
    }

    private static void returnBookFlow(LibraryManager manager) {
        System.out.println("\n--- RETURN BOOK ---");
        String loanId = readLine("Loan ID: ");

        Loan loan = manager.findLoanById(loanId);
        boolean ok = manager.returnBook(loanId);
        if (!ok) {
            System.out.println("Return failed.");
            return;
        }

        
        if (loan != null) {
            System.out.println("LateDays = " + loan.getLateDays());
            System.out.println("Fee     = " + loan.calculateLateFee());
            Book b = loan.getBook();
            if (b != null) {
                System.out.println("Availability updated: "
                        + b.getAvailableCopies() + "/" + b.getTotalCopies());
            }
        }

        System.out.println("Returned successfully.");
    }

    private static void listLoansFlow(LibraryManager manager) {
        System.out.println("\n--- LOAN LIST ---");
        if (manager.getLoans().isEmpty()) {
            System.out.println("No loans.");
            return;
        }

        for (Loan loan : manager.getLoans()) {
            String status;
            if (loan.isReturned()) {
                status = "RETURNED";
            } else {
                status = "ACTIVE";
            }

            System.out.println(loan.getLoanId()
                    + " | " + status
                    + " | Book=" + loan.getBook().getId()
                    + " | Member=" + loan.getMember().getMemberId()
                    + " | Due=" + loan.getDueDate()
                    + " | LateDays=" + loan.getLateDays());
        }
    }

    private static void searchBooksFlow(LibraryManager manager) {
        System.out.println("\n--- SEARCH BOOKS ---");
        String q = readLine("Query: ");
        var results = manager.searchBooks(q);

        if (results.isEmpty()) {
            System.out.println("No books matched.");
            return;
        }

        for (Book b : results) {
            System.out.println("- " + b.getId() + " | " + b.getTitle());
        }
    }

    private static void searchMembersFlow(LibraryManager manager) {
        System.out.println("\n--- SEARCH MEMBERS ---");
        String q = readLine("Query: ");
        var results = manager.searchMembers(q);

        if (results.isEmpty()) {
            System.out.println("No members matched.");
            return;
        }

        for (Member m : results) {
            System.out.println("- " + m.getMemberId() + " | " + m.getName());
        }
    }

   

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

   

    private static void seedDemoData(LibraryManager manager) {

      
        manager.addBook(new Book("B1", "Clean Code", "Robert C. Martin", "1111", 2));
        manager.addBook(new Book("B2", "Effective Java", "Joshua Bloch", "2222", 1));
        manager.addBook(new Book("B3", "Head First Java", "Kathy Sierra", "3333", 3));
        manager.addBook(new Book("B4", "Design Patterns", "GoF", "4444", 2));
        manager.addBook(new Book("B5", "Introduction to Algorithms", "Cormen", "5555", 2));
        manager.addBook(new Book("B6", "Refactoring", "Martin Fowler", "6666", 1));

        
        manager.addMember(new Member("M1", "Ali", "ali@example.com"));
        manager.addMember(new Member("M2", "Ayşe", "ayse@example.com"));
        manager.addMember(new Member("M3", "Mehmet", "mehmet@example.com"));

        
        manager.addMember(new StudentMember("S1", "Esra", "esra@example.com", "CENG"));
        manager.addMember(new StudentMember("S2", "Zeynep", "zeynep@example.com", "SE"));
        manager.addMember(new StudentMember("S3", "Kerem", "kerem@example.com", "EEE"));
    }
}

