package com.group.hrs.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class ReservationGUI {
	private JFrame frame = new JFrame("Main Menu");
	private JLabel instructions;
	private JPanel panel = new JPanel();
	private JPanel panel2 = new JPanel();
	JButton create = new JButton("Create Reservation");
	JButton modify = new JButton("Modify Reservation");

	Dimension screenSize;

	Reservation currentReservation;

	public ReservationGUI() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		prepareGUI();
		showGUI();
		functions();
	}

	private void functions() {

		create.addActionListener((ActionEvent event) -> {
			createForm();
		});
		
		
		modify.addActionListener((ActionEvent event) -> {
			
			try {
				modifyForm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		/*
		 * modify.addActionListener((ActionEvent event) -> { modifyForm(); });
		 * 
		 * submit.addActionListener((ActionEvent event) -> {
		 * 
		 * });
		 */

	}

	private void prepareGUI() {
		frame.setSize(screenSize);
		frame.setLayout(new GridLayout(2, 1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		instructions = new JLabel("Select an item from the menu", JLabel.CENTER);
		panel.setLayout(new FlowLayout());

		frame.add(instructions);
		frame.add(panel2);
		frame.setVisible(true);
	}

	private void showGUI() {
		panel2.setLayout(new FlowLayout());
		panel2.add(create);
		panel2.add(modify);

		panel2.add(panel);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	private void createForm() {
		RForm form = new RForm();

	}

	private void modifyForm() throws SQLException {
		JPanel panel3 = new JPanel();
		JFrame frame2 = new JFrame("Modification Form");
		frame2.setSize(200, 200);
		frame2.setLayout(new GridLayout(2, 1));
		JLabel mod = new JLabel("Modify Reservation", JLabel.CENTER);
		frame2.add(mod);
		frame2.add(panel3);

		JLabel search = new JLabel("Enter reservation ID: ", JLabel.CENTER);
		JTextField searchField = new JTextField(10);
		JButton submit = new JButton("Submit");
		panel3.setLayout(new FlowLayout());
		panel3.add(search);
		panel3.add(searchField);
		panel3.add(submit);
		frame2.setVisible(true);
		frame2.setLocationRelativeTo(null);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (search.getText().equals("")) {
					JOptionPane.showMessageDialog(frame2, "Please enter a reservation ID");
				} else {
					DatabaseLoader b = new DatabaseLoader();
					try {
						
						int temp = Integer.parseInt(searchField.getText());
						b.getReservationByID(Integer.parseInt(searchField.getText()));
					} catch (SQLException ex) {
						
					} catch (NumberFormatException ex){
						ex.printStackTrace();
						
					}
					
				}
			}
		});
	}

}