package app;

import model.Book;
import model.Member;
import model.StudentMember;
import service.LibraryManager;

import java.time.LocalDate;
import java.util.Scanner;

public class LibraryApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();

        seedDemoData(manager); // istersen kapat

        System.out.println("=== Library Management System ===");

        while (true) {
            printMenu();
            int choice = readInt("Choice: ");

            switch (choice) {
                case 1 -> addBookFlow(manager);
                case 2 -> listBooksFlow(manager);
                case 3 -> addMemberFlow(manager);
                case 4 -> listMembersFlow(manager);
                case 5 -> borrowBookFlow(manager);
                case 6 -> returnBookFlow(manager);
                case 7 -> listLoansFlow(manager);
                case 8 -> searchBooksFlow(manager);
                case 9 -> searchMembersFlow(manager);
                case 10 -> {
                    System.out.println("Bye.");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter 0-9.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1) Add book");
        System.out.println("2) List books");
        System.out.println("3) Add member");
        System.out.println("4) List members");
        System.out.println("5) Borrow book");
        System.out.println("6) Return book");
        System.out.println("7) List loans");
        System.out.println("8) Search books");
        System.out.println("9) Search members");
        System.out.println("10) Exit");
    }

    // ---------------- FLOWS ----------------

    private static void addBookFlow(LibraryManager manager) {
        System.out.println("\n--- ADD BOOK ---");
        String id = readLine("Book ID (e.g., B3): ");
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

        String memberId = readLine("Member ID (e.g., M1): ");
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
            System.out.println(m.getMemberId() + " - " + m.getName() + " | " + m.getEmail());
        }
    }

    private static void borrowBookFlow(LibraryManager manager) {
        System.out.println("\n--- BORROW BOOK ---");
        String loanId = readLine("Loan ID (e.g., L1): ");
        String bookId = readLine("Book ID: ");
        String memberId = readLine("Member ID: ");

        int loanDays = readInt("How many days? (e.g., 7): ");
        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanDate.plusDays(loanDays);

        boolean ok = manager.borrowBook(loanId, bookId, memberId, loanDate, dueDate);
        System.out.println(ok ? "Borrowed successfully." : "Borrow failed.");
    }

    private static void returnBookFlow(LibraryManager manager) {
        System.out.println("\n--- RETURN BOOK ---");
        String loanId = readLine("Loan ID: ");
        boolean ok = manager.returnBook(loanId);
        System.out.println(ok ? "Returned successfully." : "Return failed.");
    }

    private static void listLoansFlow(LibraryManager manager) {
        System.out.println("\n--- LOAN LIST ---");
        if (manager.getLoans().isEmpty()) {
            System.out.println("No loans.");
            return;
        }
        manager.getLoans().forEach(loan -> {
            String status = loan.isReturned() ? "RETURNED" : "ACTIVE";
            System.out.println(loan.getLoanId()
                    + " | " + status
                    + " | Book=" + loan.getBook().getId()
                    + " | Member=" + loan.getMember().getMemberId()
                    + " | Due=" + loan.getDueDate()
                    + " | LateDays=" + loan.getLateDays());
        });
    }

    private static void searchBooksFlow(LibraryManager manager) {
        System.out.println("\n--- SEARCH BOOKS ---");
        String q = readLine("Query: ");
        var results = manager.searchBooks(q);
        if (results.isEmpty()) {
            System.out.println("No books matched.");
            return;
        }
        results.forEach(b -> System.out.println("- " + b.getId() + " | " + b.getTitle()));
    }

    private static void searchMembersFlow(LibraryManager manager) {
        System.out.println("\n--- SEARCH MEMBERS ---");
        String q = readLine("Query: ");
        var results = manager.searchMembers(q);
        if (results.isEmpty()) {
            System.out.println("No members matched.");
            return;
        }
        results.forEach(m -> System.out.println("- " + m.getMemberId() + " | " + m.getName()));
    }

    // ---------------- INPUT HELPERS ----------------

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

    // ---------------- DEMO DATA ----------------

    private static void seedDemoData(LibraryManager manager) {
        manager.addBook(new Book("B1", "Clean Code", "Robert C. Martin", "1111", 2));
        manager.addBook(new Book("B2", "Effective Java", "Joshua Bloch", "2222", 1));
        manager.addMember(new Member("M1", "Ali", "ali@example.com"));
        manager.addMember(new Member("M2", "Ayse", "ayse@example.com"));
    }
}