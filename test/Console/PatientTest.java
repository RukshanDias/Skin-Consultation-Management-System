package Console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    private Patient patient = new Patient("Pasan", "Perera", LocalDate.parse("2003-09-30"), "0776889123", "p078");
    private static int consultationCount;

    @BeforeEach
    void setUp() {
        consultationCount = 0;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPatientId() {
        assertEquals("p078", patient.getPatientId());
    }

    @Test
    void getConsultationCount() {
        assertEquals(0, patient.getConsultationCount());
    }

    @Test
    void setPatientId() {
        patient.setPatientId("p123");
        assertEquals("p123", patient.getPatientId());
    }

    @Test
    void setConsultationCount() {
        patient.setConsultationCount(2);
        assertEquals(2, patient.getConsultationCount());
    }

    @Test
    void increaseConsultationCount() {
        patient.setConsultationCount(consultationCount += 1);
        assertEquals(1, patient.getConsultationCount());
    }
}