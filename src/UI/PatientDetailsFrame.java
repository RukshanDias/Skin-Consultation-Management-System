package UI;

import Console.Consultation;
import Console.Patient;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class PatientDetailsFrame extends JFrame {
    private Consultation consultation;
    private static ArrayList<Patient> patientList;
    private static ArrayList<Consultation> consultationList;
    private JLabel hoursLabel, addFileStatus;
    private JTextArea notesText;
    private JSlider hourSlider;
    private JButton addFileBtn, placeAppointmentBtn;
    private JTextField nameText, surnameText, dobDateText, mobileNoText, patientIdText;
    private JTextField[] textFieldList;
    private boolean isNameValid, isSurnameValid, isDobValid, isMobileNoValid, isPatientIdValid = false;

    public PatientDetailsFrame(Consultation consultation) {
        // passing consultation to attribute
        this.consultation = consultation;

        // register event handler
        ChangeHandler changeHandle = new ChangeHandler();
        ButtonHandler btnHandle = new ButtonHandler();
        KeyHandler keyHandle = new KeyHandler();

        Container c = getContentPane();
        c.setLayout(null);

        // title
        JLabel title = new JLabel("Add patient details");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        // add by patient ID
        JLabel addByPatientId = new JLabel("Enter Patient ID:");
        JTextField addByPatientIdText = new JTextField();
        JButton addPatientBtn = new JButton("Add");

        addByPatientId.setLocation(100,100);
        addByPatientId.setSize(190, 20);
        addByPatientIdText.setLocation(100,130);
        addByPatientIdText.setSize(150, 20);
        addPatientBtn.setLocation(260,130);
        addPatientBtn.setSize(60, 20);

        c.add(addByPatientId);
        c.add(addByPatientIdText);
        this.add(addPatientBtn);

        // Divider
        Line2D divider = new Line2D.Float(0,40,30,30);
//        c.add(divider);

        // labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel surnameLabel = new JLabel("Surname:");
        JLabel dobLabel = new JLabel("Date of birth:");
        JLabel mobileNoLabel = new JLabel("Mobile Number:");
        JLabel patientIdLabel = new JLabel("Patient ID:");
        hoursLabel = new JLabel("Duration:");
        JLabel notesLabel =  new JLabel("Notes:");
        JLabel [] labelList = {nameLabel, surnameLabel, dobLabel, mobileNoLabel, patientIdLabel, hoursLabel, notesLabel};

        int labelX = 430;
        int labelY = 100;
        for (JLabel label : labelList) {
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setSize(100, 20);
            label.setLocation(labelX, labelY);
            c.add(label);
            labelY += 50;
        }

        // Text fields
        nameText = new JTextField();
        surnameText = new JTextField();
        dobDateText = new JTextField();
        JTextField dobMonthText = new JTextField();
        JTextField dobYearText = new JTextField();
        mobileNoText = new JTextField();
        patientIdText = new JTextField();

        textFieldList = new JTextField[]{nameText, surnameText, dobDateText, mobileNoText, patientIdText};

        int textFiledX = 530;
        int textFiledY = 100;
        for (JTextField textField : textFieldList) {
            textField.setFont(new Font("Arial", Font.PLAIN, 15));
            textField.setSize(190, 20);
            textField.setLocation(textFiledX, textFiledY);
            textField.addKeyListener(keyHandle);
            c.add(textField);
            textFiledY += 50;
        }

        // adding other components

        // duration hourSlider
        hourSlider = new JSlider(1, 5, 1);
        hourSlider.setMajorTickSpacing(1);

        // paint the ticks and tracks
        hourSlider.setPaintTrack(true);
        hourSlider.setPaintTicks(true);
        hourSlider.setPaintLabels(true);
        hourSlider.setBounds(520,335, 200, 70);
        hourSlider.addChangeListener(changeHandle);
        c.add(hourSlider);

        // Text area for notes
        notesText = new JTextArea(5,30);
        notesText.setBounds(500,405, 230,150);
        c.add(notesText);

        // File import button
        addFileBtn = new JButton("Add file");
        addFileBtn.setBounds(740,405, 80,25);
        addFileBtn.addActionListener(btnHandle);
        c.add(addFileBtn);

        // File status
        addFileStatus = new JLabel("No file selected");
        addFileStatus.setBounds(740,435, 190,25);
        c.add(addFileStatus);

        // Place appointment button
        placeAppointmentBtn = new JButton("Place Appointment");
        placeAppointmentBtn.setBounds(240,635, 240,50);
        placeAppointmentBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        placeAppointmentBtn.addActionListener(btnHandle);
        c.add(placeAppointmentBtn);

        // setting view
        this.setTitle("Add patient details");
        this.setSize(900,800);
        this.setVisible(true);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public static void storeConsultationsData(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("consultationsData.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Consultation consultation : consultationList){
                objectOutputStream.writeObject(consultation);
            }
            objectOutputStream.close();
            System.out.println("Data successfully stored in a file..");
        }catch (IOException e){
            System.out.println("An error occurred.." + e);
        }
    }

    public static void loadConsultationsData(){
        try {
            FileInputStream fileInputStream = new FileInputStream("consultationsData.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while ((fileInputStream.available() > 0)){
                Consultation consultationObj = (Consultation) objectInputStream.readObject();

                if (!consultationList.contains(consultationObj)){
                    consultationList.add(consultationObj);
                }
                if (!patientList.contains(consultationObj.getPatient())){
                    patientList.add(consultationObj.getPatient());
                }
            }
            System.out.println("File successfully loaded..");
        }catch (IOException e){
            System.out.println("an error occurred when loading data "+ e);
        } catch (ClassNotFoundException e) {
            System.out.println("Clas not found..");
        }
    }

    public static boolean inputValidation(JTextField textField, String regex){
        boolean isInputValid;
        if (!textField.getText().matches(regex)){
            textField.setBorder(BorderFactory.createLineBorder(Color.red, 3));
            isInputValid = false;
        }else {
            textField.setBorder(BorderFactory.createLineBorder(Color.green, 2));
            isInputValid = true;
        }
        return isInputValid;
    }
    public static void markEmptyInputs(JTextField[] textField){
        for (JTextField text: textField){
            if (text.getText().isEmpty()){
                text.setBorder(BorderFactory.createLineBorder(Color.red, 3));
            }
        }
    }

    private class ChangeHandler implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == hourSlider){
                hoursLabel.setText("Duration:  " + hourSlider.getValue() + "h");
            }
        }
    }
    private class ButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addFileBtn){
                JFileChooser addFile = new JFileChooser(FileSystemView.getFileSystemView());
                int r = addFile.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION ){
                    addFileStatus.setText("Selected: " + addFile.getSelectedFile().getName());
                }else {
                    addFileStatus.setText("No file selected");
                }
            } else if (e.getSource() == placeAppointmentBtn) {
                if (!(isNameValid && isSurnameValid && isDobValid && isMobileNoValid && isPatientIdValid)){
                    markEmptyInputs(textFieldList);
                    JOptionPane.showMessageDialog(placeAppointmentBtn, "Invalid or Empty input.\nThose have marked in red.","ERROR", JOptionPane.ERROR_MESSAGE);
                }else {
                    String patientName = nameText.getText();
                    String patientSurname = surnameText.getText();
                    LocalDate patientDOB = LocalDate.parse(dobDateText.getText());
                    String patientMobileNo = mobileNoText.getText();
                    String patientID = patientIdText.getText();
                    String notes = notesText.getText();
                    int duration = hourSlider.getValue();

                    // checking new user
                    boolean isNewUser = true;
                    Patient oldPatient = null;
                    for (Patient patient: patientList){
                        if (patient.getPatientId().equals(patientID)){
                            isNewUser = false;
                            oldPatient = patient;
                            break;
                        }
                    }

                    if (isNewUser){
                        Patient newPatient = new Patient(patientName,patientSurname,patientDOB,patientMobileNo,patientID);
                        consultation.setPatient(newPatient);
                        consultation.setCost(consultation.calculateTotalCost(newPatient));
                    }else {
                        consultation.setPatient(oldPatient);
                        consultation.setCost(consultation.calculateTotalCost(oldPatient));
                    }
                    consultation.setNote(notes);

                    consultationList.add(consultation);
                    storeConsultationsData();
                }
            }
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
             if (e.getSource() == nameText) {
                isNameValid = inputValidation(nameText, "^[a-zA-Z]*$");
            } else if (e.getSource() == surnameText) {
                 isSurnameValid = inputValidation(surnameText, "^[a-zA-Z]*$");
             } else if (e.getSource() == dobDateText) {
                 try {
                     LocalDate DOB = LocalDate.parse(dobDateText.getText());
                     if(DOB.isBefore(LocalDate.now())){
                         dobDateText.setBorder(BorderFactory.createLineBorder(Color.green, 2));
                         isDobValid = true;
                     }else {
                         dobDateText.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                         isDobValid = false;
                     }
                 }catch (DateTimeParseException d){
                     dobDateText.setBorder(BorderFactory.createLineBorder(Color.red,3));
                     isDobValid = false;
                 }
             } else if (e.getSource() == mobileNoText) {
                 isMobileNoValid = inputValidation(mobileNoText, "^[0-9]{10}$");
             } else if (e.getSource() == patientIdText) {
                 isPatientIdValid = inputValidation(patientIdText, "^[a-zA-Z0-9]+$");
             }
        }
    }
}
