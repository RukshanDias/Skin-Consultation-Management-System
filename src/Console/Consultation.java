package Console;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Consultation {
    private Doctor doctor;
    private Patient patient;
    private LocalDate date;
    private LocalTime time;
    private BigDecimal cost;
    private String note;

    public Consultation(Doctor doctor, Patient patient, LocalDate date, LocalTime time, BigDecimal cost, String note) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.cost = cost;
        this.note = note;
    }
}
