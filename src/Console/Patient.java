package Console;

import java.time.LocalDate;

public class Patient extends Person{
    private int patientId;

    public Patient(String name, String surname, LocalDate DOB, String mobileNo, int patientId) {
        super(name, surname, DOB, mobileNo);
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }


}
