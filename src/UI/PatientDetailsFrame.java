package UI;

import javax.swing.*;
import java.awt.*;

public class PatientDetailsFrame extends JFrame {
    private JLabel title;

    public PatientDetailsFrame() {
        Container c = getContentPane();
        c.setLayout(null);

        // title
        title = new JLabel("Add patient details");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        // labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel surnameLabel = new JLabel("Surname:");
        JLabel dobLabel = new JLabel("Date of birth:");
        JLabel mobileNoLabel = new JLabel("Mobile Number:");
        JLabel patientIdLabel = new JLabel("Patient ID:");
        JLabel notesLabel =  new JLabel("Notes");
        JLabel [] labelList = {nameLabel, surnameLabel, dobLabel, mobileNoLabel, patientIdLabel, notesLabel};

        int labelY = 100;
        int labelX = 100;
        for (JLabel label : labelList) {
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setSize(100, 20);
            label.setLocation(labelX, labelY);
            c.add(label);
            labelY += 50;
        }

        // Text fields
        JTextField nameText = new JTextField();
        JTextField surnameText = new JTextField();
        JTextField dobDateText = new JTextField();
        JTextField dobMonthText = new JTextField();
        JTextField dobYearText = new JTextField();
        JTextField mobileNoText = new JTextField();
        JTextField patientIdText = new JTextField();
        JTextArea notesText = new JTextArea(20,30);

        JTextField[] textFieldList = {nameText, surnameText, dobDateText,mobileNoText,patientIdText};

        int textFiledX = 200;
        int textFiledY = 100;
        for (JTextField textField : textFieldList) {
            textField.setFont(new Font("Arial", Font.PLAIN, 15));
            textField.setSize(190, 20);
            textField.setLocation(textFiledX, textFiledY);
            c.add(textField);
            textFiledY += 50;
        }

        // adding other components

                // setting view
        this.setTitle("Add patient details");
        this.setSize(700,830);
        this.setVisible(true);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        new PatientDetailsFrame();
    }
}
