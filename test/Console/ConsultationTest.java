package Console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationTest {
    private Doctor doctor = new Doctor("Kasun", "Perera", LocalDate.parse("1998-09-30"), "0777889123", "d078", "Bio");
    private Patient patient = new Patient("Pasan", "Perera", LocalDate.parse("2003-09-30"), "0776889123", "p078");
    private LocalDateTime startTime = LocalDateTime.parse("2023-09-30T10:00:00");
    private LocalDateTime endTime = LocalDateTime.parse("2023-09-30T12:00:00");
    private Consultation consultation = new Consultation(doctor, patient, LocalDate.parse("2023-09-30"), 2, startTime, endTime, 30.00, "Hello", "/UI/images/doctor.png");

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calculateTotalCost() {
        int durationHours = (int) consultation.getDuration();
        long totalCost = (patient.getConsultationCount() < 1) ? durationHours * 15 : durationHours * 25;
        assertTrue(((totalCost%15 == 0) || (totalCost%25 ==0)) && totalCost > 15);
    }

    @Test
    void getDoctor() {
        assertEquals(doctor, consultation.getDoctor());
    }

    @Test
    void getPatient() {
        assertEquals(patient, consultation.getPatient());
    }

    @Test
    void getDate() {
        assertEquals(LocalDate.parse("2023-09-30"), consultation.getDate());
    }

    @Test
    void getDuration() {
        assertEquals(2, consultation.getDuration());
    }

    @Test
    void getStartTime() {
        assertEquals(startTime, consultation.getStartTime());
    }

    @Test
    void getEndTime() {
        assertEquals(endTime, consultation.getEndTime());
    }

    @Test
    void getCost() {
        assertEquals(30, consultation.getCost());
    }

    @Test
    void getNote() {
        assertEquals("Hello", consultation.getNote());
    }

    @Test
    void getImage() {
        assertEquals("/UI/images/doctor.png", consultation.getImage());
    }

    @Test
    void getSecretKey() {
    }

    @Test
    void setDoctor() {
        Doctor d1 = new Doctor("Kumar", "peris", LocalDate.parse("1978-09-30"), "0789889123", "d178", "Bio");
        consultation.setDoctor(d1);
        assertEquals(d1, consultation.getDoctor());
    }

    @Test
    void setPatient() {
        Patient p1 = new Patient("Vishwa", "fernando", LocalDate.parse("2008-09-30"), "0776889123", "p088");
        consultation.setPatient(p1);
        assertEquals(p1, consultation.getPatient());
    }

    @Test
    void setDate() {
        LocalDate newDate = LocalDate.parse("2023-12-12");
        consultation.setDate(newDate);
        assertEquals(newDate, consultation.getDate());
    }

    @Test
    void setDuration() {
        long newDuration = 3;
        consultation.setDuration(newDuration);
        assertEquals(newDuration, consultation.getDuration());
    }

    @Test
    void setStartTime() {
        LocalDateTime newStartTime = LocalDateTime.parse("2023-09-30T12:00:00");
        consultation.setStartTime(newStartTime);
        assertEquals(newStartTime, consultation.getStartTime());
    }

    @Test
    void setEndTime() {
        LocalDateTime newEndTime = LocalDateTime.parse("2023-09-30T15:00:00");
        consultation.setEndTime(newEndTime);
        assertEquals(newEndTime, consultation.getEndTime());
    }

    @Test
    void setCost() {
        double newCost = 50;
        consultation.setCost(newCost);
        assertEquals(newCost, consultation.getCost());
    }

    @Test
    void setNote() {
        String newNote = "Hello World";
        consultation.setNote(newNote);
        assertEquals(newNote, consultation.getNote());
    }

    @Test
    void setImage() {
        String newImage = "/UI/images/patient.png";
        consultation.setImage(newImage);
        assertEquals(newImage, consultation.getImage());
    }

    @Test
    void setSecretKey() {
    }
}