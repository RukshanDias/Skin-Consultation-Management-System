package Console;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consultation {
    private Doctor doctor;
    private Patient patient;
    private LocalDate date;
    private LocalTime duration;
    private double cost;
    private String note;
    private final static double normalCost = 25.00;
    private final static double firstConsulCost = 15.00;

    public Consultation(){

    }
    public Consultation(Doctor doctor, Patient patient, LocalDate date, LocalTime duration, double cost, String note) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.duration = duration;
        this.cost = cost;
        this.note = note;
    }

    public double calculateTotalCost(Patient patient){
        int durationHours = this.duration.getHour();
        double totalCost = (patient.getConsultationCount() < 1) ? durationHours * firstConsulCost : durationHours * normalCost;
        return totalCost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double calculateCostPerHour(){
        double costPerHour = (this.patient.getConsultationCount() < 1) ? firstConsulCost : normalCost;
        return costPerHour;
    }
}
