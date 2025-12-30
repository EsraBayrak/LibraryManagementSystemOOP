package model;

public class Book implements Searchable {

    private String id;
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
    private int availableCopies;

    public Book(String id, String title, String author, String isbn, int totalCopies) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Book id is required");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (totalCopies <= 0) {
            throw new IllegalArgumentException("Total copies must be positive");
        }

        this.id = id;
        this.title = title;
        this.author = author;   
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    // Setters 
    public void setId(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Book id is required");
        this.id = id;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title is required");
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTotalCopies(int totalCopies) {
        if (totalCopies <= 0) {
            throw new IllegalArgumentException("totalCopies must be positive");
        }
        
        if (availableCopies > totalCopies) {
            throw new IllegalArgumentException("availableCopies cannot exceed totalCopies");
        }
        this.totalCopies = totalCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        if (availableCopies < 0) {
            throw new IllegalArgumentException("availableCopies cannot be negative");
        }
        if (availableCopies > totalCopies) {
            throw new IllegalArgumentException("availableCopies cannot exceed totalCopies");
        }
        this.availableCopies = availableCopies;
    }

    
    public boolean borrowCopy() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    @Override
    public boolean matches(String query) {
        if (query == null || query.isBlank()) return false;

        String lower = query.toLowerCase();

        return (title != null && title.toLowerCase().contains(lower))
            || (author != null && author.toLowerCase().contains(lower))
            || (id != null && id.toLowerCase().contains(lower))
            || (isbn != null && isbn.toLowerCase().contains(lower));
    }
}

