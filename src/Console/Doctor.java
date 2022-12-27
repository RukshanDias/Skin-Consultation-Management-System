package Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class Doctor extends Person {
    private String medicalLicenseNo;
    private String specialisation;
    private ArrayList <ArrayList<LocalDateTime>> consultationTimeslots = new ArrayList<>();
    private static HashSet<String> medicalLicenseNoSet = new HashSet<>();

    public Doctor(String name, String surname, LocalDate DOB, String mobileNo, String medicalLicenseNo, String specialisation) {
        super(name, surname, DOB, mobileNo);
        this.medicalLicenseNo = medicalLicenseNo;
        this.specialisation = specialisation;
        medicalLicenseNoSet.add(medicalLicenseNo);
    }

    public String getMedicalLicenseNo() {
        return medicalLicenseNo;
    }

    public void setMedicalLicenseNo(String medicalLicenseNo) {
        this.medicalLicenseNo = medicalLicenseNo;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public static HashSet<String> getMedicalLicenseNoSet() {
        return medicalLicenseNoSet;
    }

    public ArrayList<ArrayList<LocalDateTime>> getConsultationTimeslots() {
        return consultationTimeslots;
    }

    public void addConsultationTimeslot(ArrayList<LocalDateTime> timeslot){
        this.consultationTimeslots.add(timeslot);
    }
    @Override
    public String toString() {
        return "Name: " + this.getName() + "\t\t" +
                "Surname:" + this.getSurname() + "\t\t" +
                "Date of Birth: " + this.getDOB() + "\t\t" +
                "Mobile Number: " + this.getMobileNo() + "\t\t" +
                "medicalLicenseNo: " + medicalLicenseNo + "\t\t" +
                "specialisation: " + specialisation
                ;
    }
}
