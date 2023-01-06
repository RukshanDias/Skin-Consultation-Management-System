package UI;

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

public class DoctorListFrame extends JFrame {
    private WestminsterSkinConsultationManager WSCM = new WestminsterSkinConsultationManager();
    private LinkedList <Doctor> doctorList= WSCM.getDoctorsList();
    private JButton backBtn = new JButton();
    private JButton sortBtn = new JButton();
    private JTable doctorTable = new JTable();

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

        doctorTable.setAutoCreateRowSorter(true);
        doctorTable.setModel(docTableModel);
        doctorTable.setBounds(0,40,200,300);
        doctorTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(doctorTable);

        // register event handler
        ButtonHandler buttonHandle = new ButtonHandler();
        WindowHandler windowHandle = new WindowHandler();
        this.addWindowListener(windowHandle);

        // Sort button
        sortBtn.setText("Sort");
        sortBtn.setBounds(175,50,350,40);
        sortBtn.setHorizontalAlignment(SwingConstants.RIGHT);
        sortBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/sortLogo.png")));
        sortBtn.setFocusable(false);
        sortBtn.addActionListener(buttonHandle);

        // Back button
        backBtn.setText("Back");
        backBtn.setBounds(75,50,350,40);
        backBtn.setHorizontalAlignment(SwingConstants.LEFT);
        backBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/backIcon.png")));
        backBtn.setFocusable(false);
        backBtn.addActionListener(buttonHandle);

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
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(240, 208, 144));
    }

    private class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backBtn){
                DoctorListFrame.this.dispose();
                new MainMenuFrame();
            } else if (e.getSource() == sortBtn) {
                doctorTable.getRowSorter().toggleSortOrder(1);
            }
        }
    }
    private class WindowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            DoctorListFrame.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
