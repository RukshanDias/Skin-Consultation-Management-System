package UI;

import Console.WestminsterSkinConsultationManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class MainMenuFrame extends JFrame {
    private WestminsterSkinConsultationManager WSCM = new WestminsterSkinConsultationManager();
    private JButton displayDoctorsBtn = new JButton("<html><div style='border-radius:50%;'>");
    private JButton addConsultationBtn = new JButton();
    private JButton viewHistoryBtn = new JButton();
    private final Color mainBgColor = new Color(240, 208, 144);


    public MainMenuFrame(){

        // Panels
        JPanel imgPanel = new JPanel();
        JPanel msgPanel= new JPanel();
        JPanel optionsPanel = new JPanel();
        JPanel mainContainer = new JPanel();
        JPanel footerPanel = new JPanel();

        //        Img section
        ImageIcon homeImage = new ImageIcon(getClass().getResource("/UI/images/mainMenuImg.png"));
        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(50,50,300,250);
        imgLabel.setIcon(homeImage);
        imgLabel.setHorizontalTextPosition(JLabel.CENTER);
        imgLabel.setVerticalTextPosition(JLabel.TOP);
        imgLabel.setVerticalAlignment(SwingConstants.TOP);

        imgPanel.add(imgLabel);

        // welcome msg
        JLabel welcomeMsg = new JLabel("<html><div style='text-align: center;'>Welcome to<br/> Westminster skin consultation center !!");
        welcomeMsg.setHorizontalTextPosition(JLabel.CENTER);
        Font titleFont = new Font(Font.MONOSPACED, Font.BOLD, 15);
        welcomeMsg.setFont(titleFont);

        msgPanel.add(welcomeMsg);
        msgPanel.setBackground(mainBgColor);
        msgPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10)); // adding space between panels

        // register event handler
        ButtonHandler btnHandle = new ButtonHandler();
        WindowHandler windowHandle = new WindowHandler();
        this.addWindowListener(windowHandle);

        // Menu options
        optionsPanel.setLayout(new GridLayout(3,1,20,35));

        displayDoctorsBtn.setText("View Doctor List");
        displayDoctorsBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/doctor1.png")));
        displayDoctorsBtn.setFocusable(false);
        displayDoctorsBtn.addActionListener(btnHandle);

        addConsultationBtn.setText("Add a consultation");
        addConsultationBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/calendar1.png")));
        addConsultationBtn.setFocusable(false);
        addConsultationBtn.addActionListener(btnHandle);

        viewHistoryBtn.setText("View appointment History");
        viewHistoryBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/history.png")));
        viewHistoryBtn.setFocusable(false);
        viewHistoryBtn.addActionListener(btnHandle);

        // Adding elements to panels and frame
        optionsPanel.add(displayDoctorsBtn);
        optionsPanel.add(addConsultationBtn);
        optionsPanel.add(viewHistoryBtn);
        optionsPanel.setBackground(mainBgColor);

        //
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,45,40));
        JLabel doctorCount = new JLabel("<html>Doctors <br/>Count: "+ WSCM.getDoctorsList().size());
        doctorCount.setIcon(new ImageIcon(getClass().getResource("/UI/images/doctor.png")));

        JLabel patientCount = new JLabel("<html>Patients <br/> Count: "+ PatientDetailsFrame.getPatientList().size());
        patientCount.setIcon(new ImageIcon(getClass().getResource("/UI/images/patient.png")));

        footerPanel.add(doctorCount);
        footerPanel.add(patientCount);
        footerPanel.setBackground(mainBgColor);

        mainContainer.add(imgPanel, BorderLayout.NORTH);
        mainContainer.add(msgPanel, BorderLayout.NORTH);
        mainContainer.add(optionsPanel,BorderLayout.CENTER);
        mainContainer.add(footerPanel, BorderLayout.SOUTH);
        mainContainer.setBackground(mainBgColor);


        this.add(mainContainer);

        this.setTitle("Westminster skin consultation center");
        this.setSize(400,700);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    private class ButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == displayDoctorsBtn){
                MainMenuFrame.this.dispose();
                new DoctorListFrame();
            } else if (e.getSource() == addConsultationBtn) {
                MainMenuFrame.this.dispose();
                new DoctorSelectionFrame();
            } else if (e.getSource() == viewHistoryBtn) {
                MainMenuFrame.this.dispose();
                new ViewPatientHistory();
            }
        }
    }

    private class WindowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            MainMenuFrame.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
