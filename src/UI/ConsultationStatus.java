package UI;

import Console.Consultation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class ConsultationStatus extends JFrame {
    private JButton mainMenuBtn;
    private JLabel redirectText;
    private java.util.Timer timer;
    public ConsultationStatus(boolean isSuccessful){
        // JPanels
        JPanel details = new JPanel(new GridLayout(7,2,10,5));
        // register event listeners
        ButtonHandler btnHandle = new ButtonHandler();

        // Font
        Font statusFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);

        JLabel consultationStatus = new JLabel();
        consultationStatus.setFont(statusFont);
        consultationStatus.setForeground(Color.red);

        this.add(new JLabel("Status of your appointment"));

        if (isSuccessful){
            consultationStatus.setText("Your consultation was successfully added..");
            consultationStatus.setForeground(new Color(89, 156, 3));
            this.add(consultationStatus);

            this.add(new JLabel("Details of your appointment"));
            Consultation consultation = PatientDetailsFrame.getConsultation();

            details.add(new JLabel("Patient Name :"));
            details.add(new JLabel(consultation.getPatient().getName()));
            details.add(new JLabel("Doctor Name : "));
            details.add(new JLabel("Dr." + consultation.getDoctor().getName()));
            details.add(new JLabel("Date :"));
            details.add(new JLabel(String.valueOf(consultation.getDate())));
            details.add(new JLabel("Start time :"));
            details.add(new JLabel(consultation.getStartTime().format(DateTimeFormatter.ISO_LOCAL_TIME)));
            details.add(new JLabel("End time :"));
            details.add(new JLabel(consultation.getEndTime().format(DateTimeFormatter.ISO_LOCAL_TIME)));
            details.add(new JLabel("Duration :"));
            details.add(new JLabel(String.valueOf(consultation.getDuration())));
            details.add(new JLabel("Cost :"));
            details.add(new JLabel(String.valueOf(consultation.getCost())));

        }else {
            consultationStatus.setText("Your consultation has been canceled..");
            consultationStatus.setForeground(Color.red);
            this.add(consultationStatus);

        }

        // Back to main menu Btn
        mainMenuBtn = new JButton();
        mainMenuBtn.setText("Back");
        mainMenuBtn.setBounds(75,350,350,40);
        mainMenuBtn.setHorizontalAlignment(SwingConstants.LEFT);
        mainMenuBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/backIcon.png")));
        mainMenuBtn.setFocusable(false);
        mainMenuBtn.addActionListener(btnHandle);

        redirectText = new JLabel();
        int redirectTime = 1500; // in ms

        // adding timer
        timer = new Timer();
        TimerTask countDown = new TimerTask() {
            int count = redirectTime/100;
            @Override
            public void run() {
                if (count > 0){
                    redirectText.setText("You'll redirect to MainMenu in " + count + " seconds..");
                    count--;
                }else {
                    ConsultationStatus.this.dispose();
                    new MainMenuFrame();
                    timer.cancel();
                }
            }
        };

        timer.scheduleAtFixedRate(countDown, 0, redirectTime);

        // adding element to Frame
        this.add(details);
        this.add(mainMenuBtn);
        this.add(redirectText);

        // setting view
        this.setTitle("Your appointment status");
        this.setSize(400,500);
        this.setVisible(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,200,20));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainMenuBtn){
                ConsultationStatus.this.dispose();
                timer.cancel();
                new MainMenuFrame();
            }
        }
    }
}
