package UI;

import Console.Consultation;
import Console.Doctor;
import Console.WestminsterSkinConsultationManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class DoctorSelectionFrame extends JFrame {
    WestminsterSkinConsultationManager WSCM = new WestminsterSkinConsultationManager();
    LinkedList<Doctor> doctorList= WSCM.getDoctorsList();
    private JButton backBtn = new JButton();
    private JButton nextBtn = new JButton();
    private JTable doctorTable = new JTable();
    private JRadioButton[] doctorRadioBtn = new JRadioButton[doctorList.size()];

    public DoctorSelectionFrame() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,120,20));
        JPanel doctorSelectPanel = new JPanel();

        // Table
        Object[] colNames = {"Name", "Surname", "specialisation"};
        Object data[][] = {};

        DefaultTableModel docTableModel = new DefaultTableModel(data,colNames);
        for (Doctor doctor: doctorList){
            docTableModel.addRow(new Object[] {doctor.getName(), doctor.getSurname(), doctor.getSpecialisation()});
        }

        doctorTable.setAutoCreateRowSorter(true);
        doctorTable.setModel(docTableModel);
        doctorTable.setBounds(0,40,200,300);
        doctorTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(doctorTable);

        // register event handler
        ButtonHandler buttonHandle = new ButtonHandler();

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
            doctorRadioBtn[i] = new JRadioButton(doctorList.get(i).getName());
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
        this.add(doctorSelectPanel, BorderLayout.EAST);
        this.add(scrollPane);

        // setting view
        this.setTitle("View doctors' details");
        this.setSize(700,630);
        this.setVisible(true);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        new DoctorSelectionFrame();
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
                    consultation.setDoctor(doctorList.get(selectedDoctor));
                    DoctorSelectionFrame.this.dispose();
                    new PatientDetailsFrame(consultation);
                }
            }
        }
    }
}
