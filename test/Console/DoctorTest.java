package Console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {
    private Doctor doctor = new Doctor("Kasun", "Perera", LocalDate.parse("1998-09-30"), "0777889123", "d078", "Bio");

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getMedicalLicenseNo() {
        assertEquals("d078", doctor.getMedicalLicenseNo());
    }

    @Test
    void getSpecialisation() {
        assertEquals("Bio", doctor.getSpecialisation());
    }

    @Test
    void setMedicalLicenseNo() {
        doctor.setMedicalLicenseNo("1234");
        assertEquals("1234", doctor.getMedicalLicenseNo());
    }

    @Test
    void setSpecialisation() {
        doctor.setSpecialisation("Dermatology");
        assertEquals("Dermatology", doctor.getSpecialisation());
    }

}