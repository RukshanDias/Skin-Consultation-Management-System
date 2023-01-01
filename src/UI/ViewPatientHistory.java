package UI;

import Console.Consultation;
import Console.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ViewPatientHistory extends JFrame {
    private static ArrayList<Patient> patientList = PatientDetailsFrame.getPatientList();
    private static ArrayList<Consultation> consultationsList = PatientDetailsFrame.getConsultationList();
    private JButton backBtn, patientBtn;
    private JLabel patientIdStatus;
    private JComboBox<String> searchPatientId;
    String selectedPatientId;
    ViewPatientHistory(){
        // Panels
        JPanel searchPatientPanel = new JPanel(new FlowLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,120,20));

        // register event handlers
        ButtonHandler btnHandle = new ButtonHandler();
        KeyHandler keyHandle = new KeyHandler();

        // Back button
        backBtn = new JButton();
        backBtn.setText("Back");
        backBtn.setBounds(75,50,350,40);
        backBtn.setHorizontalAlignment(SwingConstants.LEFT);
        backBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/backIcon.png")));
        backBtn.setFocusable(false);
        backBtn.addActionListener(btnHandle);

        // Title
        JLabel title = new JLabel("Select a Doctor");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        // adding to Top Panel
        topPanel.add(backBtn);
        topPanel.add(title);

        // Search patient
        JLabel patientIdLabel = new JLabel("Enter Patient ID:");

        // generate an array of patient id
        ArrayList<String> patientIdList = patientList.stream()
                .map(Patient::getPatientId)
                .collect(Collectors.toCollection(ArrayList::new));
        patientIdList.add(0,"");
        searchPatientId = new JComboBox<>(patientIdList.toArray(new String[0]));
        searchPatientId.setEditable(true);
        searchPatientId.getEditor().getEditorComponent().addKeyListener(keyHandle);
        patientBtn = new JButton("Ok");
        patientBtn.addActionListener(btnHandle);

        searchPatientPanel.add(patientIdLabel);
        searchPatientPanel.add(searchPatientId);
        searchPatientPanel.add(Box.createHorizontalStrut(30));
        searchPatientPanel.add(patientBtn);

        patientIdStatus = new JLabel("hi");

        this.add(topPanel, BorderLayout.NORTH);
        this.add(searchPatientPanel);
        this.add(patientIdStatus);


        this.setTitle("View Patient History");
        this.setSize(600,800);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 15));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    private class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == patientBtn){
                selectedPatientId = (String) searchPatientId.getEditor().getItem();

                // finding whether similar ID found
                for (Patient patient: patientList){
                    if (patient.getPatientId().equalsIgnoreCase(selectedPatientId)){
                        patientIdStatus.setText("Your patient History have displayed below.");
                        break;
                    }else {
                        patientIdStatus.setText("No patients found under "+ selectedPatientId +" ID");
                    }
                }
            } else if (e.getSource() == backBtn) {
                ViewPatientHistory.this.dispose();
                new MainMenuFrame();
            }
        }
    }
    private class KeyHandler extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource() == searchPatientId.getEditor().getEditorComponent()){
                String input = (String) searchPatientId.getEditor().getItem();
                int inputLength = input.length();
                searchPatientId.showPopup();

                // finding matching IDs
                ArrayList<String> suggestedIdList = new ArrayList<>();
                for (Patient patient: patientList){
                    String tempID = patient.getPatientId().substring(0,inputLength);
                    if (input.equalsIgnoreCase(tempID)){
                        suggestedIdList.add(patient.getPatientId());
                    }
                }

                searchPatientId.removeAllItems();
                // displaying updated suggestions
                for (String s : suggestedIdList) {
                    searchPatientId.addItem(s);
                }
                searchPatientId.getEditor().setItem(input);
            }
        }
    }
}
