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

			modifyForm();

		});

	}

	public void modifyForm() {
		String[] values = { "ID", "NAME" };

		Object selected = JOptionPane.showInputDialog(null, "Search Reservation By:", "Selection",
				JOptionPane.DEFAULT_OPTION, null, values, "0");
		if (selected != null) {// null if the user cancels.
			String selectedString = selected.toString();
			if (selectedString.equals(values[0])) {

				searchByID();
			} else if (selectedString.equals(values[1])) {

				searchByName();

			}

		} else {
			// ITS NULL
		}

	}

	public void searchByID() {

		String reservationID = JOptionPane.showInputDialog("Enter Reservation ID :");

		try {
			int id = Integer.parseInt(reservationID);
			DatabaseLoader dbl = new DatabaseLoader();
			
			
			Reservation r = dbl.getReservationByID(id);
			
			
			new RForm(r);
		
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Please enter a valid number");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void searchByName() {

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