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

}