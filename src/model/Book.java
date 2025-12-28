package model;

public class Book implements Searchable {

    private String id;
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
    private int availableCopies;

  
    public Book(String id, String title, String author, String isbn, int totalCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; 
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
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
        String lower = query.toLowerCase();

        if (title.toLowerCase().contains(lower)) {
            return true;
        }
        if (author.toLowerCase().contains(lower)) {
            return true;
        }
        if (id.toLowerCase().contains(lower)) {
            return true;
        }
        if (isbn.toLowerCase().contains(lower)) {
            return true;
        }
        return false;
    }

}
