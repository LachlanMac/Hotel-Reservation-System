package com.group.hrs.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

/*
FIGURE OUT THE 3 TIMES GET KICKED OUT THING IF USER ISN'T AUTHENTICATED
ADD THE LOGGED IN AS:
*/

public class LoginGUI extends JFrame implements ActionListener {
    
    private JFrame frame = new JFrame("Sign in");
    
    private JPanel controlPanel = new JPanel();
    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();
    
    JButton submit = new JButton("Submit");
    JButton clear = new JButton("Clear");
    
    private JLabel userLbl = new JLabel("Username:");
    private JLabel pwLbl = new JLabel("Password:");
    private JLabel welcome = new JLabel("Welcome to Hotel Reservation System!", JLabel.CENTER);
    private JLabel instructions = new JLabel("Sign in to continue", JLabel.CENTER);
    
    private JTextField userTxt = new JTextField(10);
    private JPasswordField pwTxt = new JPasswordField(10);
    
    int attempts;
    
    public LoginGUI() {
        createGUI();
        createButtons();
    }
    
    private void createButtons() {
        //submit button to authenticate user
        submit.addActionListener((ActionEvent event) -> {
            String username = userTxt.getText();
            String password = pwTxt.getText();
                if(Authenticator.validate(username, password)) {
                    ReservationGUI menu = new ReservationGUI();
                    frame.dispose();
                } 
                else
                    //while(username.equals(userTxt.getText())) {
                        if(attempts != 3) {
                            JOptionPane.showMessageDialog(this, 
                                    "Username cannot be authenticated. Try again.");
                            attempts++;
                        } else
                            JOptionPane.showMessageDialog(this, "User locked out");
                        //break;
                    //}                
        });
        //Clear button to reset input
        clear.addActionListener((ActionEvent event) -> {
            userTxt.setText("");
            pwTxt.setText("");
        });
    }
    private void createGUI() {
        
        frame.setSize(350,350);
        frame.setLayout(new BorderLayout(2, 1));
        
        controlPanel.setLayout(new FlowLayout());
        frame.add(controlPanel);
        frame.setVisible(true);
    }
    void showGUI() {
        panel.setLayout(new FlowLayout());
        FlowLayout layout = new FlowLayout();
        
        panel.setSize(200,200);
        layout.setHgap(10);
        layout.setVgap(10);
        panel.setLayout(layout);
        panel.add(welcome);
        controlPanel.add(panel);
        
        panel2.setSize(200,200);
        panel2.setLayout(layout);
        panel2.add(instructions);
        controlPanel.add(panel2);
        
        panel3.setSize(200, 200);
        panel3.setLayout(layout);
        panel3.add(userLbl);
        panel3.add(userTxt);
        controlPanel.add(panel3);
        
        panel4.setSize(200,200);
        panel4.setLayout(layout);
        panel4.add(pwLbl);
        panel4.add(pwTxt);
        controlPanel.add(panel4);
        
        panel5.setSize(200,200);
        panel5.setLayout(layout);
        panel5.add(submit);
        panel5.add(clear);
        controlPanel.add(panel5);
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}