package com.group.hrs.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/*+----------------------------------------------------------------------
||
||  Class [RForm] 
||
||         Author:  [Lachlan McCallum]
||
||    Purpose:  [This class is responsible for building forms based on creating
||				a blank reservation or modifying an existing reservation.  The form has
||				a submit function that creates a reservation object and submits it to the
||				database.]           
|+-----------------------------------------------------------------------*/
public class RForm {

	/********************************************
	 * CLASS VARIABLES
	 *******************************************/

	// String of states used in a JComboBox
	private String[] states = { "select state", "ALABAMA", "ALASKA", "ARIZONA", "ARKANSAS", "CALIFORNIA", "COLORADO",
			"CONNECTICUT", "DELAWARE", "FLORIDA", "GEORGIA", "HAWAII", "IDAHO", "ILLINOIS", "INDIANA", "IOWA", "KANSAS",
			"KENTUCKY", "LOUISIANA", "MAINE", "MARYLAND", "MASSACHUSETTS", "MICHIGAN", "MINNESOTA", "MISSISSIPPI",
			"MISSOURI", "MONTANA", "NEBRASKA", "NEVADA", "NEW HAMPSHIRE", "NEW JERSEY", "NEW MEXICO", "NEW YORK",
			"NORTH CAROLINA", "NORTH DAKOTA", "OHIO", "OKLAHOMA", "OREGON", "PENNSYLVANIA", "RHODE ISLAND",
			"SOUTH CAROLINA", "SOUTH DAKOTA", "TENNESSEE", "TEXAS", "UTAH", "VERMONT", "VIRGINIA", "WASHINGTON",
			"WEST VIRGINIA", "WISCONSIN", "WYOMING" };
	// Reservation Declaration
	private Reservation currentReservation;
	// CalendarTool Declaration
	private CalendarTool calObject;

	/********************************************
	 * SWING VARIABLES
	 *******************************************/
	// arrays to hold swing components for iteration
	private JLabel[] labels;
	private JTextField[] textFields;

	private JFrame form;
	private JPanel formPanel, calendarPanel, actionpanel;
	// combobox that holds states
	private JComboBox<String> stateCombo;
	// Layout Manager
	private SpringLayout layout;
	private JButton calendarToolButton;
	// Preferred size of form
	private Dimension dim = new Dimension(600, 600);
	// Labels
	private JLabel id_Label, fName_Label, lName_Label, streetAddress_Label, city_Label, zip_Label, email_Label,
			phone_Label, room_Label, checkin_Label, checkout_Label;
	// TextFields
	private JTextField id_Field, fName_Field, lName_Field, streetAddress_Field, city_Field, zip_Field, email_Field,
			phone_Field, room_Field, checkin_Field, checkout_Field;
	// Custom JButton classes
	JButton cancel = new JButton("Cancel Reservation");
	JButton submit = new JButton("Submit Reservation");

	/********************************************
	 * CREATE NEW FORM CONSTRUCTOR
	 *******************************************/
	public RForm() {

		// creates an empty Reservation object
		currentReservation = new Reservation();

		// creates a blank form
		createForm();

	}

	/********************************************
	 * MODIFY FORM CONSTRUCTOR
	 *******************************************/
	public RForm(Reservation r) {
		// set class variable reservation
		this.currentReservation = r;
		// if ID is Zero, then the reservation wasn't found
		if (currentReservation.getID() == 0) {
			// Display error message
			JOptionPane.showMessageDialog(new JFrame(), "Reservation ID doesn't exist.", "ID DOES NOT EXIST ERROR",
					JOptionPane.ERROR_MESSAGE);
			// exit method
			return;

		}
		// creates the form that will be populated
		loadForm();

	}

	/********************************************
	 * fillForm Method : This method fills a form with data from a reservation
	 *******************************************/
	public void fillForm() {
		// set all fields by using reservation getters
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

		// find what state matches the state in reservation and set combobox
		for (int i = 1; i < states.length; i++) {
			if (states[i].equals(currentReservation.getState())) {
				stateCombo.setSelectedItem(states[i]);
			}
		}

	}

	/********************************************
	 * buildForm Method : This method builds the initial form with out any
	 * fields set.
	 *******************************************/
	public void buildForm() {

		// Initialize JFrame and Parameters
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
		// add panels to frame with Border Layout
		form.add(formPanel, BorderLayout.CENTER);
		form.add(calendarPanel, BorderLayout.NORTH);
		form.add(actionpanel, BorderLayout.PAGE_END);
		// Create button that opens the calendar Tool
		calendarToolButton = new JButton("Calendar Tool");
		calendarToolButton.setPreferredSize(new Dimension(160, 160));
		calendarPanel.add(calendarToolButton);

		/********************************************
		 * inits all labels and fields and puts them into arrays for iteration
		 *******************************************/

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

		// Start variable to track Y Coordinates of components
		int spacing = 0;

		// For every Label and Field pair, add to JPanel and set Spring Location
		for (int k = 0; k < labels.length; k++) {
			spacing = spacing + 25;
			formPanel.add(labels[k]);
			formPanel.add(textFields[k]);
			layout.putConstraint(SpringLayout.WEST, labels[k], 50, SpringLayout.WEST, formPanel);
			layout.putConstraint(SpringLayout.NORTH, labels[k], spacing, SpringLayout.NORTH, formPanel);
			layout.putConstraint(SpringLayout.NORTH, textFields[k], spacing, SpringLayout.NORTH, formPanel);
			layout.putConstraint(SpringLayout.WEST, textFields[k], 150, SpringLayout.WEST, formPanel);

		}
		// combo box to hold states
		stateCombo = new JComboBox<String>();
		// add all states to combobox options
		for (int i = 0; i < states.length; i++) {

			stateCombo.addItem(states[i]);
		}
		// add combobxo to statepanel
		formPanel.add(stateCombo);
		// set location of combobox
		layout.putConstraint(SpringLayout.WEST, stateCombo, 220, SpringLayout.WEST, zip_Label);
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

		// If calendarToolButton is pressed, open a new calendar Tool object
		calendarToolButton.addActionListener((ActionEvent event) -> {

			calObject = new CalendarTool(this);
		});

		form.pack();
		form.setVisible(true);
		form.setLocationRelativeTo(null);

	}// END METHOD

	/********************************************
	 * createForm Method : This method is used when a reservation is created
	 * with all blank fields by the reservation GUI. The form is first built and
	 * then attempts to parse filled in data when the submit button is clicked.
	 *******************************************/

	public void createForm() {

		// enable cancel button because a reservation can be canceled if it is
		// not in the database
		cancel.setEnabled(true);
		// builds form gui
		buildForm();

		// Action listener that handles text fields by verifying it, setting
		// the reservation object fields and then submitting it into the
		// database
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// if the data in the form parses correctly
				if (verifyForm()) {
					// set reservation object fields
					setReservation();
					try {
						// new databaseloader object
						DatabaseLoader dbl = new DatabaseLoader();
						// submit reservation to database
						dbl.submitReservation(currentReservation);
						// show confirmation
						JOptionPane.showMessageDialog(null, "Reservation Created");
						try {
							BookingConfirmation confirm = new BookingConfirmation("create", currentReservation);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						submit.setBackground(Color.green);
						disableAllButtons();

					} catch (SQLException e1) {

						e1.printStackTrace();
					}

				}

			}
		});

	}

	/********************************************
	 * loadForm Method : This method is used when a reservation is loaded from
	 * the ReservationGUI. The form GUI is built, then filled in with action
	 * listeners custom for modifying the database
	 *******************************************/

	public void loadForm() {
		// Form GUI is built
		buildForm();
		// Form is filled with Reservation data
		fillForm();
		// Add cancel reservation button action listener which deletes the
		// loaded reservation from the database
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// tracks if yes or no is pressed when prompting to cancel
				// reservation
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you wish to cancel reservation?",
						"Cancel Confirmation", JOptionPane.YES_NO_OPTION);
				// if yes
				if (response == JOptionPane.YES_OPTION) {
					// create new DatabaseLoader object
					DatabaseLoader dbl = new DatabaseLoader();
					try {
						// Delete reservation by passing the ID
						dbl.deleteReservationByID(currentReservation.getID());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						BookingConfirmation delete = new BookingConfirmation("delete", currentReservation);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Show confirmation
					JOptionPane.showMessageDialog(null, "Reservation Canceled");
					// disable all buttons

					disableAllButtons();
				}

			}

		});
		// Submit button action listener that parses the input information,
		// deletes the old reservationa and replaces it with a new reservation
		submit.setText("Modify Reservation");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// verifies the text fields
				if (verifyForm()) {
					// sets the reservation object data
					setReservation();

					try {
						// new database laoder object
						DatabaseLoader dbl = new DatabaseLoader();
						// deletes the reservation from the database
						dbl.deleteReservationByID(currentReservation.getID());
						// submits a new reservation it it's place
						dbl.submitReservation(currentReservation);
						// Confirmation
						submit.setBackground(Color.green);
						JOptionPane.showMessageDialog(null, "Reservation Modified");
						disableAllButtons();

					} catch (SQLException e1) {

						e1.printStackTrace();
					}

				}

			}

		});

	}// END METHOD

	/********************************************
	 * verifyForm Method : This method verifies that the information will be
	 * parsed correctly to avoid errors or bad submits to the database. the
	 * Method checks each field to ensure that the data is valid
	 *******************************************/
	public boolean verifyForm() {
		// checks state field to make sure it is not default value
		if (stateCombo.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(form, "Please select a state.");
			return false;
		}
		// checks first name field to see if valid
		else if (fName_Field.getText().trim().length() < 2) {
			JOptionPane.showMessageDialog(form, "Please select a valid first name.");
			return false;
		}
		// checks last name field to see if valid
		else if (lName_Field.getText().trim().length() < 2) {
			JOptionPane.showMessageDialog(form, "Please select a valid last name.");
			return false;
		}
		// checks last name field to see if valid
		else if (streetAddress_Field.getText().trim().length() < 2) {
			JOptionPane.showMessageDialog(form, "Please select a valid street address.");
			return false;
		}
		// checks last name field to see if valid
		else if (city_Field.getText().trim().length() < 2) {
			JOptionPane.showMessageDialog(form, "Please select a valid city.");
			return false;
		}
		// checks zip code field
		else if (zip_Field.getText().length() != 5) {
			JOptionPane.showMessageDialog(form, "Please Enter a valid Zip Code.");
			return false;
		}
		// checks phone number field to ensure it is 10 digits long
		else if (phone_Field.getText().length() != 10) {
			JOptionPane.showMessageDialog(form, "Please Enter a 10-digit phone number.");
			return false;
		}
		// checks email field to ensure it has an @ and . symbol
		else if (!email_Field.getText().contains("@") || !email_Field.getText().contains(".")) {
			JOptionPane.showMessageDialog(form, "Please Enter a valid email Address");
			return false;
		}
		// checks check in date field to make sure it is not null
		else if (checkin_Field.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(form, "Please Select a Valid Checkin Date");
			return false;
		}
		// checks check out date field to make sure it is not null
		else if (checkout_Field.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(form, "Please Select a Valid Checkout Date");
			return false;
		}
		// checks room field to make sure it was selected
		else if (room_Field.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(form, "Please select a room.");
			return false;
		}
		// Else there are no errors and return true
		else {
			return true;
		}
	}

	/********************************************
	 * setReservation Method : This method parses the information from the text
	 * fields and fills them into the reservation object by calling setters
	 * 
	 *******************************************/

	public void setReservation() {

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

	}// END METHOD

	/********************************************
	 * disableAllButtons Method : This method disables all the fields and
	 * buttons which is used after a change has been made to the database to
	 * prevent data not syncing
	 *******************************************/
	private void disableAllButtons() {

		cancel.setEnabled(false);
		submit.setEnabled(false);

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

		calendarToolButton.setEnabled(false);

	}// END METHOD

	/********************************************
	 * GETTERSs *
	 *******************************************/

	public String getCheckout_Field() {
		return checkout_Field.getText();
	}

	public String getCheckIn_Field() {
		return checkin_Field.getText();
	}

	public Reservation getCurrentReservation() {
		return currentReservation;

	}

	public void setCheckinDate(String checkin) {

		this.checkin_Field.setText(checkin);

	}

	/********************************************
	 * SETTERS *
	 *******************************************/
	public void setCheckoutDate(String checkout) {

		this.checkout_Field.setText(checkout);

	}

	public void setRoomNumber(String roomNum) {

		this.room_Field.setText(roomNum);

	}
}// END CLASS
