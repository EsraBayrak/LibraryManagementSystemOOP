package model;

public class StudentMember extends Member {

    private String department;

    public StudentMember(String memberId, String name, String email, String department) {
        super(memberId, name, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // POLYMORPHISM: Student için indirimli ceza
    @Override
    public double calculateFee(int lateDays) {
        if (lateDays <= 0) return 0.0;
        return lateDays * 1.0; 
    }

    // INTERFACE + INHERITANCE 
    @Override
    public boolean matches(String query) {
        if (query == null || query.isBlank()) return false;

      
        if (super.matches(query)) {
            return true;
        }

       
        return department != null 
            && department.toLowerCase().contains(query.toLowerCase());
    }
}

