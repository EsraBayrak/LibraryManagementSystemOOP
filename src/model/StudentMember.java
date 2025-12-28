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
        // Member: 2.0 TL/gün
        // Student: %50 indirim => 1.0 TL/gün
        double discountedDailyFee = 1.0;
        return lateDays * discountedDailyFee;
    }

    @Override
    public boolean matches(String query) {
        if (query == null) return false;

        String lower = query.toLowerCase();

        if (getName() != null && getName().toLowerCase().contains(lower)) return true;
        if (getMemberId() != null && getMemberId().toLowerCase().contains(lower)) return true;
        if (getEmail() != null && getEmail().toLowerCase().contains(lower)) return true;
        if (department != null && department.toLowerCase().contains(lower)) return true;

        return false;
    }
}
