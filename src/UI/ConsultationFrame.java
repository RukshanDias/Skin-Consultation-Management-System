package UI;

import javax.swing.*;
import java.awt.*;


public class ConsultationFrame extends JFrame {

    public ConsultationFrame(){
        // panels
        JPanel doctorsPanel = new JPanel();
        JPanel patientsPanel = new JPanel();
        patientsPanel.setPreferredSize(new Dimension(340, 480));

        // select radio btn
        JRadioButton selectBtn1 = new JRadioButton();
        // Console.Doctor table
        String[] colNames ={"Full Name", "Specialisation"};
        String data[][] = {{"Name", "Surname"}, {"Name", "Surname"}, {"Name", "Surname"}, {"Name", "Surname"}, {"Name", "Surname"},
                {"Name", "Surname"}, {"Name", "Surname"}, {"Name", "Surname"}, {"Name", "Surname"}, {"Name", "Surname"}};

//        for (int i=0; i<5; i++){
//            Object[] rowdata = new Object[]{
//
//            }
//        }

        JTable doctorTable = new JTable(data, colNames);
        doctorTable.setBounds(0,40,100,300);
        doctorTable.setEnabled(false);

        // setting up columns width
        doctorTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        doctorTable.getColumnModel().getColumn(1).setPreferredWidth(200);

//        JScrollPane scrollPane = new JScrollPane(doctorTable);
        doctorsPanel.add(doctorTable);
        doctorsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 40, 0)); // adding space between panels


        // patients section
        patientsPanel.setBorder(BorderFactory.createTitledBorder("Console.Patient's Details"));
        GroupLayout formLayout = new GroupLayout(patientsPanel);
        formLayout.setAutoCreateGaps(true);
        formLayout.setAutoCreateContainerGaps(true);
        patientsPanel.setLayout(formLayout);

        // patient combo box
        String[] patientList = {"Dave","June"};
        JComboBox patientCb = new JComboBox(patientList);

        // Date picker


        // labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel surnameLabel = new JLabel("Surname:");
        JLabel dobLabel = new JLabel("Date of birth:");
        JLabel mobileNoLabel = new JLabel("Mobile Number:");
        JLabel patientIdLabel = new JLabel("Console.Patient ID:");
        JLabel notes =  new JLabel("Notes");

        // Text fields
        JTextField nameText = new JTextField();
        JTextField surnameText = new JTextField();
        JTextField dobText = new JTextField("yyyy-mm-dd");
        JTextField mobileNoText = new JTextField();
        JTextField patientIdText = new JTextField();
        JTextArea notesText = new JTextArea(20,30);


        formLayout.setHorizontalGroup( formLayout.createSequentialGroup()
                .addGroup( formLayout.createParallelGroup( GroupLayout.Alignment.LEADING )
                        .addComponent( nameLabel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                        .addComponent( surnameLabel )
                        .addComponent(dobLabel)
                        .addComponent(mobileNoLabel)
                        .addComponent(patientIdLabel)
                        .addComponent(notes))
                .addGroup( formLayout.createParallelGroup( GroupLayout.Alignment.LEADING )
                        .addComponent( nameText, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                        .addComponent( surnameText )
                        .addComponent(dobText)
                        .addComponent(mobileNoText)
                        .addComponent(patientIdText)
                        .addComponent(notesText, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
        );

        formLayout.setVerticalGroup( formLayout.createSequentialGroup()

                .addGroup( formLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                        .addComponent( nameLabel )
                        .addComponent( nameText ) )
                .addGroup( formLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                        .addComponent( surnameLabel )
                        .addComponent( surnameText ) )
                .addGroup( formLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                        .addComponent( dobLabel )
                        .addComponent( dobText ) )
                .addGroup( formLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                        .addComponent( mobileNoLabel )
                        .addComponent( mobileNoText ) )
                .addGroup( formLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                        .addComponent( patientIdLabel )
                        .addComponent( patientIdText ) )
                .addGroup( formLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                        .addComponent( notes )
                        .addComponent( notesText ) )

        );


        // adding to frame
        this.add(doctorsPanel, BorderLayout.NORTH);

        this.add(patientsPanel);

        // setting view
        //patientsPanel.setVisible(false);

        this.setTitle("Add consultation");
        this.setSize(700,830);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocation(650,50);
    }

    public static void main(String[] args) {
        ConsultationFrame c1= new ConsultationFrame();
    }
}
