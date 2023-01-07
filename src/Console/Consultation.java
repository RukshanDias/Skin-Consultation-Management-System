package Console;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Consultation implements Serializable {
    private Doctor doctor;
    private Patient patient;
    private LocalDate date;
    private long duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double cost;
    private String note;
    private String image;
    private SecretKeySpec secretKey;
    private final static double normalCost = 25.00;
    private final static double firstConsulCost = 15.00;

    public Consultation(){

    }
    public Consultation(Doctor doctor, Patient patient, LocalDate date, long duration, LocalDateTime startTime, LocalDateTime endTime, double cost, String note, String image) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
        this.note = note;
        this.image = image;
    }

    public double calculateTotalCost(Patient patient){
        int durationHours = (int) this.duration;
        return (patient.getConsultationCount() < 1) ? durationHours * firstConsulCost : durationHours * normalCost;
    }

   // Getters
    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getCost() {
        return cost;
    }

    public String getNote() {
        return note;
    }

    public String getImage() {
        return image;
    }

    public SecretKeySpec getSecretKey() {
        return secretKey;
    }

    // Setters

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSecretKey(SecretKeySpec secretKey) {
        this.secretKey = secretKey;
    }
}
