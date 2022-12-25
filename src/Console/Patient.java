package Console;

import java.time.LocalDate;
import java.util.HashSet;

public class Patient extends Person{
    private String patientId;
    private int consultationCount = 0;
    private static HashSet <String> patientIdSet = new HashSet<>();

    public Patient(String name, String surname, LocalDate DOB, String mobileNo, String patientId) {
        super(name, surname, DOB, mobileNo);
        this.patientId = patientId;
//        patientIdSet.add(patientId);
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getConsultationCount() {
        return consultationCount;
    }

    public void setConsultationCount(int consultationCount) {
        this.consultationCount = consultationCount;
    }
    public static void addPatientIdToSet(String patientId){
        patientIdSet.add(patientId);
    }
}
