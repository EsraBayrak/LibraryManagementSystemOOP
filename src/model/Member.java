package model;

public class Member implements Searchable {

    private String memberId;
    private String name;
    private String email;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
    public double calculateFee(int lateDays) {
        double dailyFee = 2.0;  
        return lateDays * dailyFee;
    }

    
    @Override
    public boolean matches(String query) {
        String lower = query.toLowerCase();

        if (name.toLowerCase().contains(lower)) {
            return true;
        }
        if (memberId.toLowerCase().contains(lower)) {
            return true;
        }
        if (email.toLowerCase().contains(lower)) {
            return true;
        }

        return false;
    }
}
