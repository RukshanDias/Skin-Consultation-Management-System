package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame implements ActionListener {
    private JButton displayDoctorsBtn = new JButton("<html><div style='border-radius:50%;'>");
    private JButton addConsultationBtn = new JButton();
    private JButton viewHistoryBtn = new JButton();
    private Font titleFont = new Font(Font.MONOSPACED, Font.BOLD, 15);
    private Color mainBgColor = new Color(240, 208, 144);


    public MainMenuFrame(){

        // Panels
        JPanel imgPanel = new JPanel();
        JPanel msgPanel= new JPanel();
        JPanel optionsPanel = new JPanel();
        JPanel mainContainer = new JPanel();

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
        welcomeMsg.setFont(titleFont);

        msgPanel.add(welcomeMsg);
        msgPanel.setBackground(mainBgColor);
        msgPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10)); // adding space between panels


        // Menu options
        optionsPanel.setLayout(new GridLayout(3,1,20,25));

        displayDoctorsBtn.setText("View Doctor List");
        displayDoctorsBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/doctor1.png")));
        displayDoctorsBtn.setFocusable(false);
        displayDoctorsBtn.addActionListener(this);

        addConsultationBtn.setText("Add a consultation");
        addConsultationBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/calendar1.png")));
        addConsultationBtn.setFocusable(false);
        addConsultationBtn.addActionListener(this);

        viewHistoryBtn.setText("View appointment History");
        viewHistoryBtn.setIcon(new ImageIcon(getClass().getResource("/UI/images/history.png")));
        viewHistoryBtn.setFocusable(false);
        viewHistoryBtn.addActionListener(this);

        // Adding elements to panels and frame
        optionsPanel.add(displayDoctorsBtn);
        optionsPanel.add(addConsultationBtn);
        optionsPanel.add(viewHistoryBtn);
        optionsPanel.setBackground(mainBgColor);

        mainContainer.add(imgPanel, BorderLayout.NORTH);
        mainContainer.add(msgPanel, BorderLayout.NORTH);
        mainContainer.add(optionsPanel,BorderLayout.CENTER);
        mainContainer.setBackground(mainBgColor);


        this.add(mainContainer);

        this.setTitle("Westminster skin consultation center");
        this.setSize(400,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayDoctorsBtn){
            this.dispose();
            new DoctorListFrame();
        } else if (e.getSource() == addConsultationBtn) {
            this.dispose();
            new ConsultationFrame();
        }
    }
}
