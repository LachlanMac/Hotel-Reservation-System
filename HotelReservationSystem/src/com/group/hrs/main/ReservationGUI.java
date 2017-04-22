package com.group.hrs.main;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*+----------------------------------------------------------------------
||
||  Class [ReservationGUI] 
||
||         Author:  [Kristina Villa]
||
||    Purpose:  [BLAH BLAH LBAH LBAH BLAH BLAH]
||            The CalendarTool updates as months are cycled.
||
|+-----------------------------------------------------------------------*/


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

		JTextField firstName = new JTextField(25);
		JTextField lastName = new JTextField(25);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("First Name:"));
		myPanel.add(firstName);
		myPanel.add(Box.createHorizontalStrut(5));
		myPanel.add(new JLabel("Last Name:"));
		myPanel.add(lastName);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Enter first and last name :",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			if (firstName.getText().trim().length() < 1 || lastName.getText().trim().length() < 1) {
				JOptionPane.showMessageDialog(frame, "Please Enter First and Last name");
			} else {

				DatabaseLoader dbl = new DatabaseLoader();
				try {

					JPanel p = new JPanel();

					ArrayList<Reservation> resList = new ArrayList<Reservation>();

					resList = dbl.getResByName(firstName.getText(), lastName.getText());

					if (resList.size() > 1) {

						JComboBox<String> combo = new JComboBox<String>();
						for (Reservation tempRes : resList) {

							combo.addItem(tempRes.getLastName() + " : " + tempRes.getPhoneNumber() + " : "
									+ tempRes.getEmail());

						}
						p.add(combo);
						int cResult = JOptionPane.showConfirmDialog(null, p, "Select from list of reservations:",
								JOptionPane.OK_CANCEL_OPTION);
						if (cResult == JOptionPane.OK_OPTION) {
							new RForm(resList.get(combo.getSelectedIndex()));
						}

					} else {
						new RForm(resList.get(0));

					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}
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
		new RForm();

	}

}