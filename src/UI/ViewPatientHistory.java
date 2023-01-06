package UI;

import Console.Consultation;
import Console.Patient;
import Console.WestminsterSkinConsultationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ViewPatientHistory extends JFrame {
    private WestminsterSkinConsultationManager WSCM = new WestminsterSkinConsultationManager();
    private static ArrayList<Patient> patientList = PatientDetailsFrame.getPatientList();
    private static ArrayList<Consultation> consultationsList = PatientDetailsFrame.getConsultationList();
    private ArrayList<JPanel> consultationPanelList = new ArrayList<>();
    private JButton backBtn, patientBtn;
    private JLabel patientIdStatus, imgLabel;
    JScrollPane scrollPane = new JScrollPane();
    JPanel consultationContainer = new JPanel();
    private JComboBox<String> searchPatientId;
    String selectedPatientId;

    ViewPatientHistory(){
        // Panels
        JPanel searchPatientPanel = new JPanel(new FlowLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,120,20));

        // register event handlers
        ButtonHandler btnHandle = new ButtonHandler();
        KeyHandler keyHandle = new KeyHandler();
        WindowHandler windowHandle = new WindowHandler();
        this.addWindowListener(windowHandle);

        // Back button
        backBtn = new JButton();
        backBtn.setText("Back");
        backBtn.setBounds(75,50,350,40);
        backBtn.setHorizontalAlignment(SwingConstants.LEFT);
        backBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/backIcon.png")));
        backBtn.setFocusable(false);
        backBtn.addActionListener(btnHandle);

        // Title
        JLabel title = new JLabel("Get User History");
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

        patientIdStatus = new JLabel();

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
    public ImageIcon resizeImage(String ImagePath) {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
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

                // clear previous data
                consultationPanelList.clear();
                consultationContainer.removeAll();

                // finding consultation of selected patient
                int count = 0;
                for (Consultation consultation: consultationsList){
                    if (consultation.getPatient().getPatientId().equalsIgnoreCase(selectedPatientId)){
                        consultationPanelList.add(new JPanel());
                        BoxLayout boxLayout = new BoxLayout(consultationPanelList.get(count), BoxLayout.Y_AXIS);
                        consultationPanelList.get(count).setLayout(boxLayout);

                        // adding details
                        consultationPanelList.get(count).add(new JLabel("Date : " + consultation.getDate()));
                        consultationPanelList.get(count).add(new JLabel("Patient Name : " + consultation.getPatient().getName()));
                        consultationPanelList.get(count).add(new JLabel("Doctor Name :  Dr."+ consultation.getDoctor().getName()));
                        consultationPanelList.get(count).add(new JLabel("Start time :" + consultation.getStartTime().format(DateTimeFormatter.ISO_LOCAL_TIME)));
                        consultationPanelList.get(count).add(new JLabel("End time : " + consultation.getEndTime().format(DateTimeFormatter.ISO_LOCAL_TIME)));
                        consultationPanelList.get(count).add(new JLabel("Duration : "+ consultation.getDuration() + " hour/s"));
                        consultationPanelList.get(count).add(new JLabel("Cost : €"+ consultation.getCost()));
                        consultationPanelList.get(count).add(new JLabel("Note : " + Encryptor.decryptData(consultation.getNote(), consultation.getSecretKey())));
                        consultationPanelList.get(count).add(new JLabel("Image :"));

                        // display uploaded image
                        imgLabel = new JLabel();
                        imgLabel.setSize(150,150);
                        String decryptedImg = Encryptor.decryptData(consultation.getImage(), consultation.getSecretKey());
                        if (decryptedImg.equals("N/A")){
                            imgLabel.setText("No image found");
                        }else {
                            imgLabel.setIcon(resizeImage(decryptedImg));
                            imgLabel.setForeground(Color.red);
                        }
                        consultationPanelList.get(count).add(imgLabel);

                        consultationPanelList.get(count).setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                        consultationContainer.add(consultationPanelList.get(count));

                        count++;
                    }

                }
                consultationContainer.setLayout(new GridLayout(count,1,0,10));
                scrollPane.setViewportView(consultationContainer);
                scrollPane.setPreferredSize(new Dimension(300, 400));
                ViewPatientHistory.this.add(scrollPane);

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
                try {
                    for (Patient patient: patientList){
                        String tempID = patient.getPatientId().substring(0,inputLength);
                        if (input.equalsIgnoreCase(tempID)){
                            suggestedIdList.add(patient.getPatientId());
                        }
                        patientIdStatus.setText("Searching..");
                    }
                }catch (StringIndexOutOfBoundsException c){
                    patientIdStatus.setText("No Results found..");
                }

                searchPatientId.removeAllItems();
                // displaying updated suggestions
                if (suggestedIdList.isEmpty()){
                    patientIdStatus.setText("No Results found..");
                }else {
                    for (String s : suggestedIdList) {
                        searchPatientId.addItem(s);
                    }
                }
                searchPatientId.getEditor().setItem(input);
            }
        }
    }
    private class WindowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            ViewPatientHistory.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit the program?", "Exit Program Message Box",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                super.windowClosing(e);
                WSCM.setIsGuiOpen(false);
            }else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }
}
