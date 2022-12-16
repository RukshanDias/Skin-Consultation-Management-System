package UI;

import Console.Doctor;
import Console.WestminsterSkinConsultationManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class DoctorListFrame extends JFrame implements ActionListener {
    WestminsterSkinConsultationManager westSkinMan = new WestminsterSkinConsultationManager();

    LinkedList <Doctor> doctorList= westSkinMan.getDoctorsList();
    private JButton backBtn = new JButton();
    private JButton sortBtn = new JButton();


    public DoctorListFrame(){
        // Panels
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,120,20));

        // Table
        Object colNames[] = {"Name", "Surname", "Date of Birth", "Contact No", "Medical licence No","specialisation"};
        Object data[][] = {};

        DefaultTableModel docTableModel = new DefaultTableModel(data,colNames);
        for (Doctor doctor: doctorList){
            docTableModel.addRow(new Object[] {doctor.getName(), doctor.getSurname(), doctor.getDOB(), doctor.getMobileNo(), doctor.getMedicalLicenseNo(), doctor.getSpecialisation()});
        }

        JTable doctorTable = new JTable(docTableModel);
        doctorTable.setBounds(0,40,200,300);
        doctorTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(doctorTable);

        // Sort button
        sortBtn.setText("Sort");
        sortBtn.setBounds(175,50,350,40);
        sortBtn.setHorizontalAlignment(SwingConstants.RIGHT);
        sortBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/sortLogo.png")));
        sortBtn.setFocusable(false);
        sortBtn.addActionListener(this);

        // Back button
        backBtn.setText("Back");
        backBtn.setBounds(75,50,350,40);
        backBtn.setHorizontalAlignment(SwingConstants.LEFT);
        backBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/backIcon.png")));
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);

        // Title
        JLabel title = new JLabel("Doctors details");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        // adding elements
        topPanel.add(backBtn);
        topPanel.add(title);
        topPanel.add(sortBtn);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane);

        // setting view
        this.setTitle("View doctors' details");
        this.setSize(700,830);
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocation(650,50);
    }

    public static void main(String[] args) {
        DoctorListFrame d1= new DoctorListFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn){
            this.dispose();
            new MainMenuFrame();
        }
    }
}
