package model;

public class Member implements Searchable {

    private String memberId;
    private String name;
    private String email;

    public Member(String memberId, String name, String email) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("memberId required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email required");
        }

        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

   
    public void setMemberId(String memberId) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("memberId required");
        }
        this.memberId = memberId;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name required");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email required");
        }
        this.email = email;
    }


    public double calculateFee(int lateDays) {
        if (lateDays <= 0) return 0.0;
        double dailyFee = 2.0;
        return lateDays * dailyFee;
    }

    @Override
    public boolean matches(String query) {
        if (query == null || query.isBlank()) return false;

        String lower = query.toLowerCase();

        return (name != null && name.toLowerCase().contains(lower))
            || (memberId != null && memberId.toLowerCase().contains(lower))
            || (email != null && email.toLowerCase().contains(lower));
    }
}
