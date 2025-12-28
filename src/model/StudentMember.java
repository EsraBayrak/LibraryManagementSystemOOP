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

   
    @Override
    public double calculateFee(int lateDays) {
        double dailyFee = 2.0;
        return lateDays * dailyFee * 0.5;   
    }

    @Override
    public boolean matches(String query) {
        String lower = query.toLowerCase();

        if (getName().toLowerCase().contains(lower)) {
            return true;
        }
        if (getMemberId().toLowerCase().contains(lower)) {
            return true;
        }
        if (getEmail().toLowerCase().contains(lower)) {
            return true;
        }
        if (department.toLowerCase().contains(lower)) {
            return true;
        }

        return false;
    }
}
