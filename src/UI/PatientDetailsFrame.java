package UI;

import Console.Consultation;
import Console.Patient;
import Console.WestminsterSkinConsultationManager;
import Console.Doctor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class PatientDetailsFrame extends JFrame {
    LinkedList<Doctor> doctorList= new WestminsterSkinConsultationManager().getDoctorsList();
    private static Consultation consultation;
    private static ArrayList<Patient> patientList = new ArrayList<>();
    private static ArrayList<Consultation> consultationList = new ArrayList<>();
    private JLabel addPatientStatus,hoursLabel, addFileStatus;
    private JTextArea notesText;
    private JSlider hourSlider;
    private JComboBox<String> startTimeHour, startTimeMinutes, searchPatientId;
    private JButton addPatientBtn, addFileBtn, placeAppointmentBtn, cancelBtn;
    private JTextField nameText, surnameText, dobDateText, mobileNoText, patientIdText, consultationDateText;
    private JTextField[] textFieldList;
    private boolean isNameValid, isSurnameValid, isDobValid, isMobileNoValid, isPatientIdValid, isConsulDateValid = false;

    public PatientDetailsFrame(Consultation consultation) {
        // passing consultation to attribute
        PatientDetailsFrame.consultation = consultation;
        Encryptor encryptor = new Encryptor(consultation);

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
        title.setLocation(320, 30);
        c.add(title);

        // Cancel button
        cancelBtn = new JButton();
        cancelBtn.setText("Cancel Consultation");
        cancelBtn.setBounds(85,650,250,40);
        cancelBtn.setHorizontalAlignment(SwingConstants.CENTER);
        cancelBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/cancel.png")));
        cancelBtn.setFocusable(false);
        cancelBtn.addActionListener(btnHandle);
        c.add(cancelBtn);

        // add by patient ID

        // get an arrayList of patient IDs
        ArrayList<String> patientIdList = patientList.stream()
                .map(Patient::getPatientId)
                .collect(Collectors.toCollection(ArrayList::new));
        patientIdList.add(0,"");
        searchPatientId = new JComboBox<>(patientIdList.toArray(new String[0]));
        searchPatientId.setEditable(true);
        searchPatientId.getEditor().getEditorComponent().addKeyListener(keyHandle);

        addPatientBtn = new JButton("Add");
        addPatientBtn.addActionListener(btnHandle);

        addPatientStatus = new JLabel("Enter your Patient ID, If you already have register.");
        addPatientStatus.setBounds(100,150,300,20);
        searchPatientId.setBounds(100,100,190, 20);
        addPatientBtn.setBounds(300,100,60, 20);

        c.add(searchPatientId);
        c.add(addPatientStatus);
        this.add(addPatientBtn);

        // labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel surnameLabel = new JLabel("Surname:");
        JLabel dobLabel = new JLabel("Date of birth:");
        JLabel mobileNoLabel = new JLabel("Mobile Number:");
        JLabel patientIdLabel = new JLabel("Patient ID:");
        JLabel ConsultationDateLabel = new JLabel("Date:");
        JLabel startTimeLabel = new JLabel("Start time:");
        hoursLabel = new JLabel("Duration:");
        JLabel notesLabel =  new JLabel("Notes:");
        JLabel [] labelList = {nameLabel, surnameLabel, dobLabel, mobileNoLabel, patientIdLabel, ConsultationDateLabel, startTimeLabel, hoursLabel, notesLabel};

        int labelX = 430;
        int labelY = 100;
        for (JLabel label : labelList) {
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setSize(100, 20);
            label.setLocation(labelX, labelY);
            c.add(label);
            labelY += 40;
        }

        // Text fields
        nameText = new JTextField();
        surnameText = new JTextField();
        dobDateText = new JTextField();
        mobileNoText = new JTextField();
        patientIdText = new JTextField();
        consultationDateText = new JTextField();

        textFieldList = new JTextField[]{nameText, surnameText, dobDateText, mobileNoText, patientIdText, consultationDateText};

        int textFiledX = 530;
        int textFiledY = 100;
        for (JTextField textField : textFieldList) {
            textField.setFont(new Font("Arial", Font.PLAIN, 15));
            textField.setSize(190, 20);
            textField.setLocation(textFiledX, textFiledY);
            textField.addKeyListener(keyHandle);
            c.add(textField);
            textFiledY += 40;
        }

        // adding other components
        // Start time combo Box
        String[] hours = {"08", "09", "10", "11", "12", "13", "14", "15", "16", "17"};
        String[] minutes = {"00", "15", "30", "45"};
        startTimeHour = new JComboBox<>(hours);
        startTimeHour.setBounds(530 ,340, 50,20);
        c.add(startTimeHour);
        startTimeMinutes = new JComboBox<>(minutes);
        startTimeMinutes.setBounds(590 ,340, 50,20);
        c.add(startTimeMinutes);

        // duration hourSlider
        hourSlider = new JSlider(1, 5, 1);
        hourSlider.setMajorTickSpacing(1);

        // paint the ticks and tracks
        hourSlider.setPaintTrack(true);
        hourSlider.setPaintTicks(true);
        hourSlider.setPaintLabels(true);
        hourSlider.setBounds(520,355, 200, 70);
        hourSlider.addChangeListener(changeHandle);
        c.add(hourSlider);

        // Text area for notes
        notesText = new JTextArea(5,30);
        notesText.setBounds(500,425, 230,150);
        c.add(notesText);

        // File import button
        addFileBtn = new JButton("Add file");
        addFileBtn.setBounds(740,435, 80,25);
        addFileBtn.addActionListener(btnHandle);
        c.add(addFileBtn);

        // File status
        addFileStatus = new JLabel("No file selected");
        addFileStatus.setBounds(740,465, 190,25);
        c.add(addFileStatus);

        // File preview

        // Place appointment button
        placeAppointmentBtn = new JButton("Place Appointment");
        placeAppointmentBtn.setBounds(440,645, 240,50);
        placeAppointmentBtn.setFont(new Font("Arial", Font.PLAIN, 17));
        placeAppointmentBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/add.png")));
        placeAppointmentBtn.addActionListener(btnHandle);
        c.add(placeAppointmentBtn);

        // setting view
        this.setTitle("Add patient details");
        this.setSize(900,800);
        this.setVisible(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    public static ArrayList<Patient> getPatientList() {
        return patientList;
    }
    public static ArrayList<Consultation> getConsultationList() {
        return consultationList;
    }
    public static Consultation getConsultation() {
        return consultation;
    }


    public static void storeConsultationsData(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("consultationsData.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Consultation consultation : consultationList){
                objectOutputStream.writeObject(consultation);
            }
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Consultation data successfully stored in a file..");
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
                consultationList.add(consultationObj);

                if (!patientList.contains(consultationObj.getPatient())){
                    patientList.add(consultationObj.getPatient());
                }
            }
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Consultation data successfully loaded..");
        }catch (IOException e){
            System.out.println("an error occurred when loading data "+ e);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found..");
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
    public boolean isDoctorAvailable (Doctor doctor, LocalDateTime startTime, LocalDateTime endTime){
        boolean doctorAvailability = true;
        for (Consultation consul : consultationList) {
            if (Objects.equals(consul.getDoctor().getMedicalLicenseNo(), doctor.getMedicalLicenseNo())) {
                // does time overlap condition
                if ((consul.getStartTime().isBefore(startTime) && consul.getEndTime().isAfter(startTime)) ||
                        (consul.getStartTime().isBefore(endTime) && consul.getEndTime().isAfter(endTime)) ||
                        (consul.getStartTime().isBefore(startTime) && consul.getEndTime().isAfter(endTime)) ||
                        (consul.getStartTime().isAfter(startTime) && consul.getEndTime().isBefore(endTime)) ||
                        consul.getStartTime().isEqual(startTime) || consul.getEndTime().isEqual(endTime))
                {
                    doctorAvailability = false;
                    break;
                }
            }
        }
        return doctorAvailability;
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
            if (e.getSource() == addPatientBtn){
                String selectedPatientId = (String) searchPatientId.getEditor().getItem();

                // finding whether similar ID found
                for (Patient patient: patientList){
                    if (patient.getPatientId().equalsIgnoreCase(selectedPatientId)){
                        // add details to text filed
                        nameText.setText(patient.getName());
                        surnameText.setText(patient.getSurname());
                        dobDateText.setText(patient.getDOB().toString());
                        mobileNoText.setText(patient.getMobileNo());
                        patientIdText.setText(patient.getPatientId());

                        for (int i=0; i<textFieldList.length-1; i++){
                            textFieldList[i].setEditable(false);
                            textFieldList[i].setBorder(BorderFactory.createLineBorder(Color.green, 2));
                        }
                        addPatientStatus.setText("Patient "+ selectedPatientId +" details has been added");
                        isNameValid = true;
                        isSurnameValid = true;
                        isDobValid = true;
                        isMobileNoValid = true;
                        isPatientIdValid = true;
                        break;
                    }else {
                        addPatientStatus.setText("No patients found under "+ selectedPatientId +" ID");
                    }
                }

            } else if (e.getSource() == addFileBtn){
                // initialize file chooser & and filter
                JFileChooser addFile = new JFileChooser(FileSystemView.getFileSystemView());
                FileNameExtensionFilter validFiles = new FileNameExtensionFilter("Image Files", "bmp", "jpg", "jpeg", "wbmp", "png", "gif");
                addFile.setFileFilter(validFiles);

                int r = addFile.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION ){
                    addFileStatus.setText("Selected: " + addFile.getSelectedFile().getName());

                    // encrypt image path & store
                    String imagePath = addFile.getSelectedFile().toString();
                    String encryptedImgPath = Encryptor.encryptData(imagePath);
                    consultation.setImage(encryptedImgPath);
                }else {
                    addFileStatus.setText("No file selected");
                }

            } else if (e.getSource() == cancelBtn) {
                PatientDetailsFrame.this.dispose();
                new ConsultationStatus(false, false);

            } else if (e.getSource() == placeAppointmentBtn) {
                if (!(isNameValid && isSurnameValid && isDobValid && isMobileNoValid && isPatientIdValid && isConsulDateValid)){
                    markEmptyInputs(textFieldList);
                    JOptionPane.showMessageDialog(placeAppointmentBtn, "Invalid or Empty input.\nThose have marked in red.","ERROR", JOptionPane.ERROR_MESSAGE);
                }else {
                    // get entered details
                    String patientName = nameText.getText();
                    String patientSurname = surnameText.getText();
                    LocalDate patientDOB = LocalDate.parse(dobDateText.getText());
                    String patientMobileNo = mobileNoText.getText();
                    String patientID = patientIdText.getText().trim();
                    LocalDate consultationDate = LocalDate.parse(consultationDateText.getText());
                    LocalTime startTime = LocalTime.parse(startTimeHour.getSelectedItem() +":"+startTimeMinutes.getSelectedItem());
                    String notes = notesText.getText();
                    long duration = hourSlider.getValue();

                    // converting dateTime
                    LocalDateTime consultationStartDateTime = consultationDate.atTime(startTime);
                    LocalTime endTime = startTime.plusHours(duration);
                    LocalDateTime consultationEndDateTime = consultationDate.atTime(endTime);

                    // add time data to consultation
                    consultation.setDuration(duration);

                    // doctor availability
                    boolean isNewDoctor;
                    if (! isDoctorAvailable(consultation.getDoctor(), consultationStartDateTime, consultationEndDateTime)){
                        Random random = new Random();
                        while (true){
                            int randomDoc = random.nextInt(doctorList.size());
                            if (isDoctorAvailable(doctorList.get(randomDoc),consultationStartDateTime, consultationEndDateTime)){
                                consultation.setDoctor(doctorList.get(randomDoc));
                                isNewDoctor = true;
                                break;
                            }
                        }
                    }else {
                        isNewDoctor = false;
                    }

                    // checking new user
                    boolean isNewUser = true;
                    Patient oldPatient = null;
                    for (Patient patient: patientList){
                        if (patient.getPatientId().equalsIgnoreCase(patientID)){
                            isNewUser = false;
                            oldPatient = patient;
                            break;
                        }
                    }

                    // add data to patient obj
                    if (isNewUser){
                        Patient newPatient = new Patient(patientName,patientSurname,patientDOB,patientMobileNo,patientID);
                        consultation.setPatient(newPatient);
                        consultation.setCost(consultation.calculateTotalCost(newPatient));
                        newPatient.increaseConsultationCount();
                        patientList.add(newPatient);
                    }else {
                        consultation.setPatient(oldPatient);
                        consultation.setCost(consultation.calculateTotalCost(oldPatient));
                        oldPatient.increaseConsultationCount();
                    }

                    // encrypt note
                    String encryptedNote = Encryptor.encryptData(notes);

                    // add data to class
                    consultation.setDate(consultationDate);
                    consultation.setStartTime(consultationStartDateTime);
                    consultation.setEndTime(consultationEndDateTime);
                    consultation.setNote(encryptedNote);

                    // store/add consultation data
                    consultationList.add(consultation);

                    // open new frame
                    PatientDetailsFrame.this.dispose();
                    new ConsultationStatus(true, isNewDoctor);
                }
            }
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            // search PatientById comboBox
            if (e.getSource() == searchPatientId.getEditor().getEditorComponent()){
                String input = (String) searchPatientId.getEditor().getItem();
                int inputLength = input.length();
                searchPatientId.showPopup();

                // finding matching IDs
                ArrayList<String> suggestedIdList = new ArrayList<>();

                try {
                    for (Patient patient: patientList){
                        String tempID = patient.getPatientId().substring(0,inputLength);
                        if (input.equalsIgnoreCase(tempID)){
                            suggestedIdList.add(patient.getPatientId());
                        }
                        addPatientStatus.setText("Searching..");
                    }
                }catch (StringIndexOutOfBoundsException c){
                    addPatientStatus.setText("No Results found..");
                }


                searchPatientId.removeAllItems();
                // displaying updated suggestions
                for (String s : suggestedIdList) {
                    searchPatientId.addItem(s);
                }
                searchPatientId.getEditor().setItem(input);
            }
             else if (e.getSource() == nameText) {
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
             } else if (e.getSource() == consultationDateText) {
                try {
                    LocalDate consulDate = LocalDate.parse(consultationDateText.getText());
                    if(consulDate.isAfter(LocalDate.now())){
                        consultationDateText.setBorder(BorderFactory.createLineBorder(Color.green, 2));
                        isConsulDateValid = true;
                    }else {
                        consultationDateText.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                        isConsulDateValid = false;
                    }
                }catch (DateTimeParseException d){
                    consultationDateText.setBorder(BorderFactory.createLineBorder(Color.red,3));
                    isConsulDateValid = false;
                }
            }
        }
    }
}
