package com.group.hrs.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;

public class RDate extends JButton {
	/*
	 * +----------------------------------------------------------------------
	 * || || Class [RDate] ||
	 * Author: [Lachlan McCallum]
	 * Purpose: [This class is used as an extension of a button that can be || used in the
	 * CalendarGUI. This button has several variables that can || hold and
	 * manipulate date based data.] || || Inherits From: [RDate inherits from
	 * Swing Component JButton]
	 * |+-----------------------------------------------------------------------
	 */

	/********************************************
	 * CLASS VARIABLES
	 *******************************************/
	// Refernce to CalendarTool that created the Button
	private CalendarTool calendarTool;
	// Holds boolean specifying if this specific date is check in or check out
	private boolean checkinSelected = false, checkoutSelected = false;

	/********************************************
	 * COLOR VARIABLES
	 *******************************************/
	Color checkinColor = new Color(102, 102, 225);
	Color checkoutColor = new Color(102, 102, 225);
	Color inbetweenColor = new Color(194, 194, 255);

	/********************************************
	 * VARIABLES THAT HELP TRACK DATE
	 *******************************************/
	private int day, month, year;
	private String display;
	private Calendar cal;
	Date buttonDate;

	/********************************************
	 * MAXIMUM RESERVATION LENGTH ALLOWED
	 *******************************************/
	private final int MAX_RESERVATION_LENGTH = 7;

	/********************************************
	 * CONSTRUCTOR
	 *******************************************/
	public RDate(CalendarTool calendarTool, int day, int month, int year) {
		// CALL SUPER CLASS CONSTRUCTOR TO INIT JBUTTON
		super();
		// if date is not valid, set text to empty string
		if (day == 0)
			display = "";
		else {
			// String that equates to the day numberr
			display = Integer.toString(day);
		}
		//sets the text of the button to equal the display string
		this.setText(display);
		// Assign class variables to hold date data
		this.day = day;
		this.month = month;
		this.year = year;
		cal = Calendar.getInstance();
		cal.set(year, month, day);
		buttonDate = cal.getTime();
		this.calendarTool = calendarTool;

		// STYLE SETTINGS
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.setBackground(Color.WHITE);

		// Adds an action listener to the button to add behavior if pressed
		addListener();

	}

	/********************************************
	 * addListener Method: This method adds an action listener to the RDate
	 * button to perform a Behavior when it is pressed.
	 *******************************************/
	public void addListener() {

		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				// if this button is already selected and the checkout date
				// hasn't been selected
				if (checkinSelected && calendarTool.getCheckoutDate() == null) {
					// un select this button
					deselectCheckinDate();
					return;
				}
				// if this button is the checkout
				if (checkoutSelected) {
					// un select this button
					deselectCheckoutDate();
					return;
				}
				// if check in hasn't yet been selected
				if (!checkinSelected) {
					// if an initial date hasn't been selected
					if (calendarTool.getCheckinDate() == null) {
						// select this date as a check in
						selectCheckinDate();

					} else { // check in date is not null
						// if the check out date hasnt been selected
						if (calendarTool.getCheckoutDate() == null) {
							// and, the check out date is valid and falls within
							// the max time
							if (isValidCheckoutDate() && reservationTime()) {
								// select the check out date
								selectCheckoutDate();

							}
						}

					}
				}

			}

		});

	}// END METHOD

	/********************************************
	 * selectCheckInDate Method: This method flags this object as being the
	 * checkin date and also updating the calendarTool GUI
	 *******************************************/
	public void selectCheckinDate() {
		// flags this object as the check in date object
		checkinSelected = true;
		// updates calendar tool to reflect the change
		calendarTool.setCheckinDate(this);
		// colors this button as an indicator on the calendar tool
		this.setBackground(checkinColor);
		// button style
		this.setOpaque(true);

	}// END METHOD

	/********************************************
	 * selectCheckOutDate Method: This method flags this object as being the
	 * checkout date and also updating the calendarTool GUI
	 *******************************************/
	public void selectCheckoutDate() {
		// flags this as the checkout date object
		checkoutSelected = true;
		// sets the calendarTool checkout date
		calendarTool.setCheckoutDate(this);
		// changes this button to be colored
		this.setBackground(checkoutColor);
		// button style
		this.setOpaque(true);
		// Color the dates between check out and check in
		colorInbetween();

	}// END METHOD

	/********************************************
	 * deselectCheckOutDate Method: This method flags this object as not being
	 * the check out date. The gui is updated to reflect that
	 *******************************************/
	public void deselectCheckoutDate() {
		// flags this object as false for check out date
		checkoutSelected = false;
		// returns button to normal status
		this.setBackground(Color.white);
		// updates calendar tool
		calendarTool.setCheckoutDate(null);
		calendarTool.resetRoomList();
		// resets all dates between check in and checkout to normal status
		unColorInbetween();
	}// END METHOD

	/********************************************
	 * selectCheckInDate Method: This method flags this object as not being the
	 * check in date and also updating the calendarTool GUI
	 *******************************************/
	public void deselectCheckinDate() {
		// flags this object as false for check in date
		checkinSelected = false;
		// returns button to normal status
		this.setBackground(Color.white);
		// updates calendarTool
		calendarTool.setCheckinDate(null);
	}// END METHOD

	/********************************************
	 * softSelect Method: This method colors this object a color that represents
	 * being in between check in and check out dates
	 *******************************************/
	public void softSelect() {
		this.setBackground(inbetweenColor);
		this.setOpaque(true);
	}// END METHOD

	/********************************************
	 * colorInbetween Method: This method colors this object a color that
	 * represents being in between check in and check out dates
	 *******************************************/
	public void colorInbetween() {
		// For the list of dates in the array
		for (RDate jd : calendarTool.getDateList()) {
			// if the date falls in between check in and check out
			if (jd.inbetweenDates()) {
				// color dates
				jd.setBackground(inbetweenColor);
			}

		}
	}// END METHOD

	/********************************************
	 * uncolorInbetween Method: This method resets the color back to normal
	 * after a checkout date is deselected.
	 *******************************************/
	public void unColorInbetween() {
		// for all the dates in the array list
		for (RDate jd : calendarTool.getDateList()) {
			// if the RDate objects are not the check in or check out date
			if (!jd.equalsDate(calendarTool.getCheckinDate()) && !jd.equalsDate(calendarTool.getCheckoutDate())) {
				// and if the date is before the current date
				if (jd.getDate().before(Calendar.getInstance().getTime())) {
					// disable button and set color
					jd.setBackground(Color.LIGHT_GRAY);
					jd.setEnabled(false);
				} else {
					// or else reset the button to normal
					jd.setBackground(Color.white);
				}
			}
		}
	}// END METHOD

	/********************************************
	 * isValidCheckoutDate Method: This method ensures that a checkout date
	 * cannot be set before a check in date
	 *******************************************/
	public boolean isValidCheckoutDate() {

		if (calendarTool.getCheckinDate().getButtonDate().before(this.getButtonDate())) {

			return true;

		} else {
			return false;
		}

	}//END METHOD

	/********************************************
	 * equalsDate Method: This method is used as a comparator to determine if
	 * two dates are equal based on the date, the month and the year.
	 *******************************************/
	public boolean equalsDate(RDate date) {
		//if the parameter passed in is null, return false
		if (date == null)
			return false;
		//if the year, month and day are equal, return true
		if (this.getYear() == date.getYear() && this.getMonth() == date.getMonth() && this.getDay() == date.getDay()) {
			return true;
		} else {//they are not equal
			return false;
		}
	}//END METHOD
	
	/********************************************
	 * reservationTime Method:  This method ensures that a given date is within the 
	 * maximum alloted reservation time allowed by the program which is hard coded as 7
	 *******************************************/
	public boolean reservationTime() {
		//sets the callendar to the date of this RDate minus the reservation time
		cal.set(this.getYear(), this.getMonth(), this.getDay() - MAX_RESERVATION_LENGTH);
		//Compares the created date with the selected date to ensure that it is within 7 says
		if (cal.getTime().before(calendarTool.getCheckinDate().getButtonDate())) {
			cal.set(this.getYear(), this.getMonth(), this.getDay() + MAX_RESERVATION_LENGTH);
			return true;
		} else {
			//resets the date of the calendar
			cal.set(this.getYear(), this.getMonth(), this.getDay() + MAX_RESERVATION_LENGTH);
			//displays a message stating that the reservation is too long
			JOptionPane.showMessageDialog(calendarTool, "Maximum Reservation number is "+ MAX_RESERVATION_LENGTH +" days");
			//return false
			return false;
		}

	}//END METHOD

	/********************************************
	 * inbeetweenDates Method: This method is a utility to determine if a date 
	 * is in between the checkin and checkout date
	 *******************************************/
	public boolean inbetweenDates() {

		if (calendarTool.getCheckinDate().getButtonDate().before(this.getButtonDate())
				&& calendarTool.getCheckoutDate().getButtonDate().after(this.getButtonDate())) {
			return true;
		}

		else {
			return false;
		}

	}

	//OVERWRITTEN TO STRING METHOD FOR TESTING PURPOSES
	@Override
	public String toString() {

		// return buttonDate.toString();

		return Integer.toString(this.getYear()) + "-" + Integer.toString(this.getMonth()) + "-"
				+ Integer.toString(this.getDay());

	}

	
	/********************************************
	 				* GETTERS *
	 *******************************************/
	
	public Date getDate() {		return cal.getTime();	}

	public int getDay() {  return day;  }

	public int getMonth() { return month;  }

	public int getYear() {  return year;  }

	public Date getButtonDate() {  return buttonDate;  }
}
