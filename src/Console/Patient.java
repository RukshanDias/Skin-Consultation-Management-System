package Console;

import java.time.LocalDate;
import java.util.HashSet;

public class Patient extends Person{
    private String patientId;
    private int consultationCount = 0;

    public Patient(String name, String surname, LocalDate DOB, String mobileNo, String patientId) {
        super(name, surname, DOB, mobileNo);
        this.patientId = patientId;
    }

    // getters
    public String getPatientId() {
        return patientId;
    }
    public int getConsultationCount() {
        return consultationCount;
    }

    // setters
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    public void setConsultationCount(int consultationCount) {
        this.consultationCount = consultationCount;
    }

    public void increaseConsultationCount(){
        this.consultationCount += 1;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", consultationCount=" + consultationCount +
                '}';
    }
}
