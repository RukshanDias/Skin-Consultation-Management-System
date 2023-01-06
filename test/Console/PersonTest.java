package Console;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private Doctor doctor = new Doctor("Kasun", "Perera", LocalDate.parse("1998-09-30"), "0777889123", "d078", "Bio");

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getName() {
        assertEquals("Kasun", doctor.getName());
    }

    @Test
    void getSurname() {
        assertEquals("Perera", doctor.getSurname());
    }

    @Test
    void getDOB() {
        assertEquals(LocalDate.parse("1998-09-30"), doctor.getDOB());
    }

    @Test
    void getMobileNo() {
        assertEquals("0777889123", doctor.getMobileNo());
    }

    @Test
    void setName() {
        doctor.setName("Dimuth");
        assertEquals("Dimuth", doctor.getName());
    }

    @Test
    void setSurname() {
        doctor.setSurname("Peris");
        assertEquals("Peris", doctor.getSurname());
    }

    @Test
    void setDOB() {
        doctor.setDOB(LocalDate.parse("2001-02-02"));
        assertEquals(LocalDate.parse("2001-02-02"), doctor.getDOB());
    }

    @Test
    void setMobileNo() {
        doctor.setMobileNo("0778899123");
        assertEquals("0778899123", doctor.getMobileNo());
    }
}