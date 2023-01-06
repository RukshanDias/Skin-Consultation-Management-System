package UI;

import Console.Consultation;
import Console.Doctor;
import Console.WestminsterSkinConsultationManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class DoctorSelectionFrame extends JFrame {
    WestminsterSkinConsultationManager WSCM = new WestminsterSkinConsultationManager();
    LinkedList<Doctor> doctorList= WSCM.getDoctorsList();
    private JButton backBtn = new JButton();
    private JButton nextBtn = new JButton();
    private JTable doctorTable = new JTable();
    private JRadioButton[] doctorRadioBtn = new JRadioButton[doctorList.size()];

    public DoctorSelectionFrame() {
        // JPanels
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,120,20));
        JPanel doctorSelectPanel = new JPanel();

        // Table
        Object[] colNames = {"Name", "Surname", "specialisation"};
        Object[][] data = {};

        DefaultTableModel docTableModel = new DefaultTableModel(data,colNames);
        for (Doctor doctor: doctorList){
            docTableModel.addRow(new Object[] {doctor.getName(), doctor.getSurname(), doctor.getSpecialisation()});
        }

        doctorTable.setAutoCreateRowSorter(true);
        doctorTable.setModel(docTableModel);
        doctorTable.setBounds(0,40,200,100);
        doctorTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        scrollPane.setPreferredSize(new Dimension(500, 100));

        // register event handler
        ButtonHandler buttonHandle = new ButtonHandler();
        WindowHandler windowHandle = new WindowHandler();
        this.addWindowListener(windowHandle);

        // Back button
        backBtn.setText("Back");
        backBtn.setBounds(75,50,350,40);
        backBtn.setHorizontalAlignment(SwingConstants.LEFT);
        backBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/backIcon.png")));
        backBtn.setFocusable(false);
        backBtn.addActionListener(buttonHandle);

        // Title
        JLabel title = new JLabel("Select a Doctor");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        // Radio Btn - Doctor select
        doctorSelectPanel.setLayout(new BoxLayout(doctorSelectPanel, BoxLayout.Y_AXIS));
        doctorSelectPanel.add(new JLabel("Select a Doctor"));

        ButtonGroup buttonGroup = new ButtonGroup();
        int locationY = 150;
        for (int i=0; i<doctorList.size(); i++){
            String fullName = doctorList.get(i).getName() +" "+ doctorList.get(i).getSurname();
            doctorRadioBtn[i] = new JRadioButton("Dr." + fullName);
            buttonGroup.add(doctorRadioBtn[i]);
            doctorRadioBtn[i].setBounds(120,locationY,120,50);
            doctorSelectPanel.add(doctorRadioBtn[i]);
            locationY += 10;
        }
        nextBtn.setText("Next");
        nextBtn.addActionListener(buttonHandle);
        doctorSelectPanel.add(nextBtn);

        // adding elements
        topPanel.add(backBtn);
        topPanel.add(title);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane);
        this.add(doctorSelectPanel, BorderLayout.SOUTH);

        // setting view
        this.setTitle("Select a Doctor");
        this.setSize(700,630);
        this.setVisible(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public int getSelectedRadioBtn(JRadioButton[] doctorRadioBtn){
        for (int i=0; i<doctorRadioBtn.length; i++){
            if (doctorRadioBtn[i].isSelected()){
                return i;
            }
        }
        return -1; // nothing selected
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backBtn){
                DoctorSelectionFrame.this.dispose();
                new MainMenuFrame();
            } else if (e.getSource() == nextBtn) {
                int selectedDoctor = getSelectedRadioBtn(doctorRadioBtn);
                if (selectedDoctor < 0){
                    JOptionPane.showMessageDialog(nextBtn, "Pls select a Doctor","ERROR", JOptionPane.ERROR_MESSAGE);
                }else {
                    Consultation consultation = new Consultation();
                    consultation.setDoctor(doctorList.get(selectedDoctor)); // setting a doctor
                    DoctorSelectionFrame.this.dispose();
                    new PatientDetailsFrame(consultation);
                }
            }
        }
    }
    private class WindowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            DoctorSelectionFrame.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
