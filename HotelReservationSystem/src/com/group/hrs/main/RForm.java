package com.group.hrs.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class RForm {

	String[] states = { "select state", "ALABAMA", "ALASKA", "ARIZONA", "ARKANSAS", "CALIFORNIA", "COLORADO",
			"CONNECTICUT", "DELAWARE", "FLORIDA", "GEORGIA", "HAWAII", "IDAHO", "ILLINOIS", "INDIANA", "IOWA", "KANSAS",
			"KENTUCKY", "LOUISIANA", "MAINE", "MARYLAND", "MASSACHUSETTS", "MICHIGAN", "MINNESOTA", "MISSISSIPPI",
			"MISSOURI", "MONTANA", "NEBRASKA", "NEVADA", "NEW HAMPSHIRE", "NEW JERSEY", "NEW MEXICO", "NEW YORK",
			"NORTH CAROLINA", "NORTH DAKOTA", "OHIO", "OKLAHOMA", "OREGON", "PENNSYLVANIA", "RHODE ISLAND",
			"SOUTH CAROLINA", "SOUTH DAKOTA", "TENNESSEE", "TEXAS", "UTAH", "VERMONT", "VIRGINIA", "WASHINGTON",
			"WEST VIRGINIA", "WISCONSIN", "WYOMING" };

	Reservation currentReservation;
	CalendarTool calObject;

	JLabel[] labels;
	JTextField[] textFields;
	JFrame form;
	JPanel formPanel, calendarPanel, actionpanel;
	JComboBox<String> stateCombo;
	SpringLayout layout;
	JButton calendarTool;
	Dimension dim = new Dimension(600, 600);

	JLabel id_Label, fName_Label, lName_Label, streetAddress_Label, city_Label, zip_Label, email_Label, phone_Label,
			room_Label, checkin_Label, checkout_Label;

	JTextField id_Field, fName_Field, lName_Field, streetAddress_Field, city_Field, zip_Field, email_Field, phone_Field,
			room_Field, checkin_Field, checkout_Field;

	JButton cancel = new JButton("Cancel Reservation");
	JButton submit = new JButton("Submit Reservation");

	public RForm() {
		createForm();
		cancel.setEnabled(false);
	}

	public RForm(Reservation r) {

		this.currentReservation = r;
		
		if(currentReservation.getID() == 0){
			JOptionPane.showMessageDialog(new JFrame(), "Reservation ID doesn't exist.",
					"ID DOES NOT EXIST ERROR", JOptionPane.ERROR_MESSAGE);
			return;
			
		}
		loadForm();

		cancel.setEnabled(true);

		fillForm();

	}

	public Reservation getCurrentReservation() {
		return currentReservation;

	}

	public void fillForm() {
		
		id_Field.setText(Integer.toString(currentReservation.getID()));
		fName_Field.setText(currentReservation.getFirstName());
		lName_Field.setText(currentReservation.getLastName());
		streetAddress_Field.setText(currentReservation.getStreet());
		city_Field.setText(currentReservation.getCity());
		zip_Field.setText(currentReservation.getZip());
		email_Field.setText(currentReservation.getEmail());
		phone_Field.setText(currentReservation.getPhoneNumber());
		room_Field.setText(Integer.toString(currentReservation.getRoom()));
		checkin_Field.setText(currentReservation.getInDate());
		checkout_Field.setText(currentReservation.getOutDate());

		for (int i = 1; i < states.length; i++) {
			if (states[i].equals(currentReservation.getState())) {
				stateCombo.setSelectedItem(states[i]);
			} else {
				
			}
		}

	}

	public String getCheckout_Field() {
		return checkout_Field.getText();
	}

	public String getCheckIn_Field() {
		return checkin_Field.getText();
	}

	public void buildForm() {
		form = new JFrame();
		form.setTitle("Reservation Form");
		form.setSize(dim);
		form.setPreferredSize(dim);
		form.setLocationRelativeTo(null);
		form.setLayout(new BorderLayout());
		layout = new SpringLayout();

		calendarPanel = new JPanel();

		actionpanel = new JPanel();
		actionpanel.setLayout(new FlowLayout());
		actionpanel.add(submit);
		actionpanel.add(cancel);

		formPanel = new JPanel();
		formPanel.setLayout(layout);

		form.add(formPanel, BorderLayout.CENTER);
		form.add(calendarPanel, BorderLayout.NORTH);
		form.add(actionpanel, BorderLayout.PAGE_END);

		calendarTool = new JButton("Calendar Tool");
		calendarTool.setPreferredSize(new Dimension(160, 160));
		calendarPanel.add(calendarTool);

		calendarTool.addActionListener((ActionEvent event) -> {

			calObject = new CalendarTool(this);
		});

		id_Label = new JLabel("ID:");
		id_Field = new JTextField(5);
		id_Field.setEditable(false);

		fName_Label = new JLabel("First Name:");
		fName_Field = new JTextField(25);

		lName_Label = new JLabel("Last Name:");
		lName_Field = new JTextField(25);

		streetAddress_Label = new JLabel("Street Address: ");
		streetAddress_Field = new JTextField(25);

		city_Label = new JLabel("City:");
		city_Field = new JTextField(25);

		zip_Label = new JLabel("Zip Code:");
		zip_Field = new JTextField(5);

		email_Label = new JLabel("Email Address:");
		email_Field = new JTextField(30);

		phone_Label = new JLabel("Phone Number:");
		phone_Field = new JTextField(10);

		room_Label = new JLabel("Room Number:");
		room_Field = new JTextField(5);
		room_Field.setEditable(false);

		checkin_Label = new JLabel("Check-in Date:");
		checkin_Field = new JTextField(10);
		checkin_Field.setEditable(false);

		checkout_Label = new JLabel("Check-out Date:");
		checkout_Field = new JTextField(10);
		checkout_Field.setEditable(false);

		labels = new JLabel[] { id_Label, fName_Label, lName_Label, streetAddress_Label, city_Label, zip_Label,
				email_Label, phone_Label, room_Label, checkin_Label, checkout_Label };
		textFields = new JTextField[] { id_Field, fName_Field, lName_Field, streetAddress_Field, city_Field, zip_Field,
				email_Field, phone_Field, room_Field, checkin_Field, checkout_Field };

		int spacing = 0;

		for (int k = 0; k < labels.length; k++) {
			spacing = spacing + 25;
			formPanel.add(labels[k]);
			formPanel.add(textFields[k]);
			layout.putConstraint(SpringLayout.WEST, labels[k], 50, SpringLayout.WEST, formPanel);
			layout.putConstraint(SpringLayout.NORTH, labels[k], spacing, SpringLayout.NORTH, formPanel);
			layout.putConstraint(SpringLayout.NORTH, textFields[k], spacing, SpringLayout.NORTH, formPanel);
			layout.putConstraint(SpringLayout.WEST, textFields[k], 150, SpringLayout.WEST, formPanel);

		}

		stateCombo = new JComboBox<String>();

		for (int i = 0; i < states.length; i++) {

			stateCombo.addItem(states[i]);
		}

		formPanel.add(stateCombo);
		// this sets the Top left corner?
		layout.putConstraint(SpringLayout.WEST, stateCombo, 220, SpringLayout.WEST, zip_Label);
		// this sets the Y axis?
		layout.putConstraint(SpringLayout.NORTH, stateCombo, 0, SpringLayout.NORTH, zip_Label);
		JLabel state_Label = new JLabel("State:");
		formPanel.add(state_Label);
		layout.putConstraint(SpringLayout.NORTH, state_Label, 0, SpringLayout.NORTH, zip_Label);
		layout.putConstraint(SpringLayout.WEST, state_Label, 120, SpringLayout.WEST, zip_Field);

		form.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {

				form.dispose();

			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Reservation currentReservation = new Reservation();

				currentReservation.setCheckInDate(checkin_Field.getText());
				currentReservation.setCheckOutDate(checkout_Field.getText());

				currentReservation.calculateDates(currentReservation.getInDate(), currentReservation.getOutDate());

			}
		});

		form.pack();
		form.setVisible(true);
		form.setLocationRelativeTo(null);

	}

	private void disableAllButtons() {

		id_Field.setEnabled(false);
		fName_Field.setEnabled(false);
		lName_Field.setEnabled(false);
		streetAddress_Field.setEnabled(false);
		city_Field.setEnabled(false);
		zip_Field.setEnabled(false);
		email_Field.setEnabled(false);
		phone_Field.setEnabled(false);
		room_Field.setEnabled(false);
		checkin_Field.setEnabled(false);
		checkout_Field.setEnabled(false);
		stateCombo.setEnabled(false);
		
		calendarTool.setEnabled(false);

	}

	public void loadForm() {
		
		
		
		
		
		buildForm();

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you wish to cancel reservatioN?",
						"cancel confirmation", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {

					// CANCEL RESERVATION

					JOptionPane.showMessageDialog(null, "Reservation Canceled");
					cancel.setEnabled(false);
					submit.setEnabled(false);
					disableAllButtons();
				} else {

					// RESERVATION NOT CANCELED

				}

			}

		});

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (currentReservation.getState().equals("select state")) {
					JOptionPane.showMessageDialog(form, "Please select a state.");

				}

				else if (zip_Field.getText().length() != 5) {
					JOptionPane.showMessageDialog(form, "Please Enter a valid Zip Code.");

				}

				else if (phone_Field.getText().length() != 10) {
					JOptionPane.showMessageDialog(form, "Please Enter a valid Phone number like '5555555555'.");

				}

				else if (!email_Field.getText().contains("@") || !email_Field.getText().contains(".")) {
					JOptionPane.showMessageDialog(form, "Please Enter a valid email Address");

				} else if (calObject.getCheckinDate() == null) {
					JOptionPane.showMessageDialog(form, "Please Select a Valid Checkin Date");

				} else if (calObject.getCheckoutDate() == null) {
					JOptionPane.showMessageDialog(form, "Please Select a Valid Checkout Date");

				}

				else if (room_Field.getText().equals("")) {
					JOptionPane.showMessageDialog(form, "Select a room!");

				} else {
					submit.setBackground(Color.green);

					currentReservation.setFirstName(fName_Field.getText());

					currentReservation.setLastName(lName_Field.getText());

					currentReservation.setStreet(streetAddress_Field.getText());

					currentReservation.setCity(city_Field.getText());

					currentReservation.setState(stateCombo.getSelectedItem().toString());

					currentReservation.setPhone(phone_Field.getText());

					currentReservation.setZip(zip_Field.getText());

					currentReservation.setCheckInDate(checkin_Field.getText());

					currentReservation.setEmail(email_Field.getText());

					currentReservation.setCheckOutDate(checkout_Field.getText());

					currentReservation.setRoom(Integer.parseInt(room_Field.getText()));

					try {

						DatabaseLoader dbl = new DatabaseLoader();
						dbl.submitReservation(currentReservation);
						
						JOptionPane.showMessageDialog(null, "Reservation Submitted");
						disableAllButtons();

					} catch (SQLException e1) {

						e1.printStackTrace();
					}

				}

			}
		});

	}

	public void createForm() {

		buildForm();

		currentReservation = new Reservation();

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (currentReservation.getState().equals("select state")) {
					JOptionPane.showMessageDialog(form, "Please select a state.");

				}

				else if (zip_Field.getText().length() != 5) {
					JOptionPane.showMessageDialog(form, "Please Enter a valid Zip Code.");

				}

				else if (phone_Field.getText().length() != 10) {
					JOptionPane.showMessageDialog(form, "Please Enter a valid Phone number like '5555555555'.");

				}

				else if (!email_Field.getText().contains("@") || !email_Field.getText().contains(".")) {
					JOptionPane.showMessageDialog(form, "Please Enter a valid email Address");

				} else if (calObject.getCheckinDate() == null) {
					JOptionPane.showMessageDialog(form, "Please Select a Valid Checkin Date");

				} else if (calObject.getCheckoutDate() == null) {
					JOptionPane.showMessageDialog(form, "Please Select a Valid Checkout Date");

				}

				else if (room_Field.getText().equals("")) {
					JOptionPane.showMessageDialog(form, "Select a room!");

				} else {
					submit.setBackground(Color.green);

					currentReservation.setFirstName(fName_Field.getText());

					currentReservation.setLastName(lName_Field.getText());

					currentReservation.setStreet(streetAddress_Field.getText());

					currentReservation.setCity(city_Field.getText());

					currentReservation.setState(stateCombo.getSelectedItem().toString());

					currentReservation.setPhone(phone_Field.getText());

					currentReservation.setZip(zip_Field.getText());

					currentReservation.setCheckInDate(checkin_Field.getText());

					currentReservation.setEmail(email_Field.getText());

					currentReservation.setCheckOutDate(checkout_Field.getText());

					currentReservation.setRoom(Integer.parseInt(room_Field.getText()));

					try {

						DatabaseLoader dbl = new DatabaseLoader();
						dbl.submitReservation(currentReservation);

					} catch (SQLException e1) {

						e1.printStackTrace();
					}

				}

			}
		});

	}

	public void setCheckinDate(String checkin) {

		this.checkin_Field.setText(checkin);

	}

	public void setCheckoutDate(String checkout) {

		this.checkout_Field.setText(checkout);

	}

	public void setRoomNumber(String roomNum) {

		this.room_Field.setText(roomNum);

	}
}
