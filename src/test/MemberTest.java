package test;

import model.Member;
import model.StudentMember;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    @Test
    void member_fee_is2PerDay() {
        Member m = new Member("M1", "Ali", "a@a.com");
        assertEquals(6.0, m.calculateFee(3));
    }

    @Test
    void student_fee_isDiscounted() {
        StudentMember s = new StudentMember("S1", "Esra", "e@e.com", "CENG");
        assertEquals(3.0, s.calculateFee(3));
    }

    @Test
    void fee_isPolymorphic_whenReferencedAsMember() {
        Member m = new StudentMember("S1", "Esra", "e@e.com", "CENG");
        assertEquals(3.0, m.calculateFee(3));
    }
}


