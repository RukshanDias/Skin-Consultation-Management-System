package Console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterSkinConsultationManagerTest {
    private static final int MAX_DOCTORS_COUNT = 7;
    private static LinkedList<Doctor> doctorsList = new LinkedList<>();
    private static boolean isGuiOpen = false;
    private String nameTest, birthday, mobileNoTest, idTest;
    private Doctor d1;

    @BeforeEach
    void setUp() {
        nameTest = "Jason";
        birthday = "2002-02-12";
        mobileNoTest = "0887867566";
        idTest = "d078";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addDoctor() {
        d1 = new Doctor("Kusal", "Perera", LocalDate.parse("1978-12-23"), "0778263544", "d789", "bio");
        doctorsList.add(d1);
        assertTrue(doctorsList.contains(d1), "Doctor did not added to the system");
    }

    @Test
    void removeDoctor() {
        doctorsList.remove(d1);
        assertFalse(doctorsList.contains(d1));
    }

    @Test
    void displayDoctors() {
    }

    @Test
    void getDoctorsList() {
    }

    @Test
    void setIsGuiOpen() {
        assertFalse(isGuiOpen);
    }

    @Test
    void stringValidation() {
        String userInput = nameTest;
        String inputType = "name";
        String condition = "";
        switch (inputType) {
            case "name", "text", "surname" -> {
                condition = "^[a-zA-Z]*$";
            }
            case "mobileNo" -> {
                condition = "^[0-9]{10}$";
            }
            case "Id" -> {
                condition = "^[a-zA-Z0-9]+$";
            }
        }
        assertTrue(userInput.matches(condition), "User input is invalid");
    }

    @Test
    void dateValidation() {
        LocalDate DOB;
        String str = birthday;
        DOB = LocalDate.parse(str);
        assertTrue(DOB.isBefore(LocalDate.now()), "invalid date of birth");
    }
}