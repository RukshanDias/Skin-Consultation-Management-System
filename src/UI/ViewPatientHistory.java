package UI;

import Console.Consultation;
import Console.Patient;

import java.util.ArrayList;

public class ViewPatientHistory {
    ArrayList<Patient> patientsList = PatientDetailsFrame.getPatientList();
    ArrayList<Consultation> consultationsList = PatientDetailsFrame.getConsultationList();

    ViewPatientHistory(){

    }
}
