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
    private static int consultationCount = 0;
    private final static double normalCost = 25.00;
    private final static double firstConsulCost = 15.00;

    public Consultation(Doctor doctor, Patient patient, LocalDate date, LocalTime duration, double cost, String note) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.duration = duration;
        this.cost = cost;
        this.note = note;
    }
    public boolean isFirstConsultation(){
        if (consultationCount > 1){
            return false;
        }return true;
    }

    public double calculateTotalCost(){
        int durationHours = this.duration.getHour();
        double totalCost = (isFirstConsultation()) ? durationHours * firstConsulCost : durationHours * normalCost;
        return totalCost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
