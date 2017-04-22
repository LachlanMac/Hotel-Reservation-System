package com.group.hrs.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


/*+----------------------------------------------------------------------
||
||  Class [CalendarTool] 
||
||         Author:  [Lachlan McCallum]
||
||    Purpose:  [This class displays a calendar GUI tool that is used 
||            to select a check in date, check out date and a room number.]
||            The CalendarTool updates as months are cycled.
||    
||    Inherits From:  [CalendarTool inherits from Swing Component JFrame]
|+-----------------------------------------------------------------------*/

public class CalendarTool extends JFrame {

	/********************************************
	 * SWING COMPONENT VARIABLES
	 *******************************************/
	private JPanel roomPane, contentPane, helperPane, calendarPane, datePane, uiPane, mfPane;
	private JButton room1, room2, room3, room4, room5, room6, room7, room8;
	private JButton[] buttonArray = { room1, room2, room3, room4, room5, room6, room7, room8 };
	private JLabel dateDisplay;
	// CUSTOM ARROW SHAPED JBUTTON CLASSES
	private PreviousButton previousButton;
	private NextButton nextButton;
	// FORM JFRAME REFERENCE VARIABLE
	private RForm form;
	// REFERENCES TO JBUTTONS THAT ARE MARKED CHECKIN/CHECKOUT
	private RDate checkinDate, checkoutDate;
	/********************************************
	 * UI COLOR VARIABLES
	 *******************************************/
	private Color cOccupied = new Color(190, 0, 0);
	private Color cVacant = new Color(34, 139, 34);

	/********************************************
	 * UI SIZING SPECIFICATIONS
	 *******************************************/
	private final int WINDOW_WIDTH = 900, WINDOW_HEIGHT = 700;
	private Dimension buttonSize = new Dimension(50, 40);
	private Dimension calendarSize = new Dimension(400,400);

	/********************************************
	 * DATE TRACKING VARIABLES
	 *******************************************/
	private Date date;
	private Calendar calendar;
	private DateFormat df = new SimpleDateFormat("MMM - yyyy");
	private ArrayList<RDate> dateList;
	private int currentYear, currentMonth, daysInMonth, firstDayOfMonth;

	/********************************************
	 * CONSTRUCTOR
	 *******************************************/
	public CalendarTool(RForm form) {
		// sets form reference to passed parameter
		this.form = form;
		// initialize dateList Array to track all dates that have been created
		dateList = new ArrayList<RDate>();
		// initialize calendar class
		calendar = Calendar.getInstance();
		// initialize date class
		date = calendar.getTime();
		// saves year variable from calendar
		currentYear = calendar.get(Calendar.YEAR);
		// saves the month variable from calendar
		currentMonth = calendar.get(Calendar.MONTH);
		// retrieves the number of days in a month
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		// sets the calendar to the first of the month
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		// Window Listener that disposes of Calendar as the default close
		// operation
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		// Method that builds resting point GUI
		buildGUI();
		// Method that updates the calendar to display current month
		updateCalendar(0);

	} // END CONSTRUCTOR

	/********************************************
	 * updateRoomList Method : This method takes an array to determine what
	 * rooms are occupied and vacant based on the check in and check out date.
	 * The GUI Is updated accordingly by coloring and disabling buttons
	 *******************************************/
	public void updateRoomList(boolean[] rArray) {

		for (int i = 0; i < buttonArray.length; i++) {

			if (rArray[i + 1] == false) {
				buttonArray[i].setEnabled(true);
				buttonArray[i].setBackground(cVacant);
				buttonArray[i].setForeground(Color.WHITE);
				buttonArray[i].setText("Room " + (i + 1) + " : Vacant");

			} else {
				buttonArray[i].setEnabled(false);
				buttonArray[i].setBackground(cOccupied);
				buttonArray[i].setForeground(Color.WHITE);
				buttonArray[i].setText("Room " + (i + 1) + " : Occupied");

			}

		}

	} // END METHOD

	/********************************************
	 * updateRoomList Method : This method takes an array to determine what
	 * rooms are occupied and vacant based on the check in and check out date.
	 * The GUI Is updated accordingly by coloring and disabling buttons
	 *******************************************/
	public void resetRoomList() {

		for (int i = 0; i < buttonArray.length; i++) {
			// SET TO DISABLED INITIALLY
			buttonArray[i].setEnabled(false);
			// SETS COLOR TO GRAY
			buttonArray[i].setBackground(Color.GRAY);
			// SETS THE TEXT INSIDE THE BUTTON TO DISPLAY THE ROOM NUMBER AND
			// STATUS
			buttonArray[i].setText("Room " + (i + 1));
		}

	} // END METHOD

	/********************************************
	 * initRoomButtons Method : This method iterates through an array of buttons
	 * and sets their default to be disabled as well as stylistic GUI properties
	 *******************************************/
	public void initRoomButtons() {

		for (int i = 0; i < buttonArray.length; i++) {
			// INIT BUTTON
			buttonArray[i] = new JButton();
			// SET TO DISABLED INITIALLY
			buttonArray[i].setEnabled(false);
			// SETS COLOR TO GRAY
			buttonArray[i].setBackground(Color.GRAY);
			// SETS THE SIZE OF THE BUTTON
			buttonArray[i].setPreferredSize(buttonSize);
			// SETS THE TEXT INSIDE THE BUTTON TO DISPLAY THE ROOM NUMBER AND
			// STATUS
			buttonArray[i].setText("Room " + (i + 1));
			// ADDS BORDER FOR STYLE
			buttonArray[i].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			// ADD BUTTON TO JPANEL
			roomPane.add(buttonArray[i]);
			// ADD ACTION LISTENER
			addRoomListener(buttonArray[i], i);

		}

	} // END METHOD

	/********************************************
	 * addRoomListener Method : This method adds a listener to the room buttons
	 * that allows a vacant room to be selected then updated on the JFrame form
	 *******************************************/
	public void addRoomListener(JButton button, int ID) {
		// add action listener to button

		button.addActionListener((ActionEvent event) -> {

			// if clicked, the room number field in form will be set to the ID
			// of the button
			form.setRoomNumber(Integer.toString(ID + 1));
			// closes the calendar GUI Tool
			this.dispose();
		});

	}// END METHOD

	/********************************************
	 * updateCalendar Method : This Method updates the Calendar by taking in an
	 * integer that represents the next month. a Value of -1 means the previous
	 * month and +1 means the next month. 0 will display the current month. Once
	 * the month is set, the month will be displayed in the CalendarGUI via the
	 * buildMonth method
	 *******************************************/
	public void updateCalendar(int newMonth) {
		// VARIABLE TO HOLD NEW MONTH INTEGER
		currentMonth = newMonth + currentMonth;
		// SETS CALENDAR TO BE THE NEW MONTH
		calendar.set(Calendar.MONTH, currentMonth);
		// UPDATES THE YEAR INT HE CASE THAT THE NEW MONTH IS IN A DIFFERENT
		// YEAR
		currentYear = calendar.get(Calendar.YEAR);
		// UPDATES THE MONTH VARIABLE TO HAVE THE NEW MONTH
		currentMonth = calendar.get(Calendar.MONTH);
		// CALCULATES NUMBER OF DAYS IN THE NEW MONTH
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); //
		// CALCULATES THE DAY THAT THE 1ST FALLS ON (IE. MONDAY, TUESDAY, ETC)
		firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
		// SETS THE DATE VARIABLE WITH THE UPDATED CALENDAR INFORMATION
		date = calendar.getTime();
		// CALLS METHOD THAT UPDATES GUI
		buildMonth();

	} // END METHOD

	/********************************************
	 * buildGUI Method : This method uses SWING to build a function calendar
	 * based GUI. The JFrame is updated to include style elements as well as
	 * adding of the panels to the frame.
	 *******************************************/
	public void buildGUI() {
		// SET JFRAME SETTINGS SECTION
		setTitle("HRS Calendar Tool");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

		// INITIALZES PANES AND SETS LAYOUTS
		calendarPane = new JPanel();
		calendarPane.setLayout(new BorderLayout());

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		mfPane = new JPanel();
		mfPane.setLayout(new GridLayout(0, 7));

		uiPane = new JPanel();
		uiPane.setLayout(new FlowLayout());

		helperPane = new JPanel();
		helperPane.add(new JLabel("Select Rooms"));

		roomPane = new JPanel();
		roomPane.setLayout(new GridLayout(0, 2));

		contentPane.add(roomPane, BorderLayout.PAGE_END);
		contentPane.add(helperPane, BorderLayout.CENTER);

		calendarPane.add(mfPane, BorderLayout.PAGE_START);
		calendarPane.add(uiPane, BorderLayout.PAGE_END);

		// ADD 2 MAIN PANELS TO FRAME
		this.add(calendarPane, BorderLayout.PAGE_START);
		this.add(contentPane, BorderLayout.PAGE_END);

		// INITILAIZES CUSTOM ARROW BUTTON OBJECTS
		nextButton = new NextButton();
		previousButton = new PreviousButton();
		// INTIALIZES LABELS
		dateDisplay = new JLabel();

		// ADDS BUTTONS TO PANE
		uiPane.add(previousButton);
		uiPane.add(dateDisplay);
		uiPane.add(nextButton);

		// ADDS DAY OF WEEK DISPLAY TO CALENDAR
		mfPane.add(new JButton("Sunday")).setEnabled(false);
		mfPane.add(new JButton("Monday")).setEnabled(false);
		mfPane.add(new JButton("Tuesday")).setEnabled(false);
		mfPane.add(new JButton("Wednesday")).setEnabled(false);
		mfPane.add(new JButton("Thursday")).setEnabled(false);
		mfPane.add(new JButton("Friday")).setEnabled(false);
		mfPane.add(new JButton("Saturday")).setEnabled(false);

		// INITIALIZES THE ROOM BUTTONS TO DEFAULT VALUES
		initRoomButtons();

		// DISPLAYS THE MONTH AND YEAR BASED ON DATE
		dateDisplay.setText(df.format(date));

		// ACTION LISTENER SECTION

		previousButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				// REDRAWS THE CALENDAR TO PREVIOUS MONTH
				updateCalendar(-1);
				dateDisplay.setText(df.format(date));

			}

		});

		nextButton.addActionListener(new ActionListener() {
			// REDRAWS THE CALENDAR TO NEXT MONTH
			@Override
			public void actionPerformed(ActionEvent event) {

				updateCalendar(1);
				dateDisplay.setText(df.format(date));
			}
		});

	}// END METHOD

	/********************************************
	 * buildMonth Method : This method updates the main calendar panel to draw a
	 * calendar GUI. the panel is removed and then recreated as the months are
	 * cycled.
	 *******************************************/

	public void buildMonth() {
		// IF THIS ISNT FIRST TIME BUILD, REMOVE THE PANE AND REBUILD IT
		if (datePane != null) {
			calendarPane.remove(datePane);
			datePane.removeAll();
		}
		// INITIALIZE PANEL AND SET STYLE
		datePane = new JPanel();
		datePane.setLayout(new GridLayout(0, 7));
		datePane.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		datePane.setPreferredSize(calendarSize);

		// ADDS THE DATE PANE TO THE CALENDAR PANE
		calendarPane.add(datePane);

		// SETS THE DAYS OF THE WEEK BEFORE THE 1st OF THE MONTH TO DISPLAY
		// EMPTY JBUTTON
		for (int j = 1; j < firstDayOfMonth; j++) {
			datePane.add(new JButton("")).setEnabled(false);
		}
		// FOR ALL THE DAYS OF THE MONTH
		for (int i = 1; i <= daysInMonth; i++) {
			// CREATE A NEW RDATE AND PASS IN DATE INFORMATION
			RDate d = new RDate(this, i, currentMonth, currentYear);
			// DISABLES DATES IN THE MONTH THAT HAVE ALREADY PASSED
			if (d.getDate().before(Calendar.getInstance().getTime())) {
				d.setEnabled(false);
				d.setBackground(Color.LIGHT_GRAY);
			}
			// ADDS THE DATA TO AN ARRAY LIST THAT TRACKS WHAT DATES HAVE BEEN
			// SELECTED
			dateList.add(d);
			// ADDS THE RDATE JBUTTON TO THE CALENDAR
			datePane.add(d);

			// SECTION THAT KEEPS TRACK OF DATA SO DATA IS RETAINED EVEN AFTER
			// CYLCING THROUGH MONTHS

			if (checkinDate != null && d.equalsDate(checkinDate)) {

				d.selectCheckinDate();
			}
			if (checkoutDate != null && d.equalsDate(checkoutDate)) {

				d.selectCheckoutDate();
			}
			if (checkoutDate != null && checkinDate != null) {
				if (d.inbetweenDates()) {

					d.softSelect();
				}
			}
		}
		// PACKS FRAME FOR DISPLAY
		this.pack();

	}

	/********************************************
	 * GETTERS *
	 *******************************************/

	public ArrayList<RDate> getDateList() {
		return dateList;
	}

	public RForm getForm() {
		return form;
	}

	public RDate getCheckinDate() {
		return checkinDate;
	}

	public RDate getCheckoutDate() {
		return checkoutDate;
	}

	/********************************************
	 * SETTERS *
	 *******************************************/

	/********************************************
	 * setCheckinDate and setCheckoutDate METHOD: These methods takes a rDate
	 * object as a parameter and marks it as the check in or check out date. The
	 * methods then update the form to display the date that was selected
	 *******************************************/
	public void setCheckinDate(RDate date) {
		// sets class variable to parameter
		this.checkinDate = date;
		// protects against null date being passed into method
		if (date != null)
			// sets form check in date field to display date
			form.setCheckinDate(checkinDate.toString());
		// if the check in date is unselected
		if (checkinDate == null) {
			// set the check in date on the form to be empty
			form.setCheckinDate("");
		}
	} // END METHOD

	public void setCheckoutDate(RDate date) {

		this.checkoutDate = date;
		if (date != null) {
			form.setCheckoutDate(checkoutDate.toString());

			try {

				boolean[] tempArray = getMasterRoomList(
						form.getCurrentReservation().calculateDates(form.getCheckIn_Field(), form.getCheckout_Field()));
				updateRoomList(tempArray);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		if (checkoutDate == null) {
			form.setCheckoutDate("");
		}
	} // END METHOD

	/********************************************
	 * getMasterRoomList returns an array that represents if there are conflicts
	 * in the reservation trying to be made and the reservations already in the
	 * database The method takes in an array of dates and checks them against
	 * the data base before returning a boolean array that represents true =
	 * occuped, false = vacant
	 */
	public boolean[] getMasterRoomList(ArrayList<String> reservationDates) throws SQLException {
		// create databaseloader object
		DatabaseLoader dbl = new DatabaseLoader();
		// initiliaze new array
		boolean[] masterList = new boolean[9];
		// set all fields to false
		for (int i = 0; i < masterList.length; i++) {
			masterList[i] = false;
		}
		// for every single date in reservationDate
		for (String dates : reservationDates) {
			// retrieve an array of rooms booked for that date from database
			boolean[] tempList = dbl.LookUpByDate(dates);
			// cycle through that array and look for occupied rooms
			for (int k = 1; k < tempList.length; k++) {
				// if a room is occupied
				if (tempList[k] == true) {
					// update the master list so that room cannot be used in
					// this reservation
					masterList[k] = true;

				}

			}

		}

		return masterList;

	}

}// END CLASS

/*
 * +---------------------------------------------------------------------- || ||
 * Class [NextButton and PreviousButton] || || Author: [Lachlan McCallum] || ||
 * Purpose: [These classes create a custom JButton that uses polygons to ||
 * change shape to a triangle used to intuitively cycle through months ||
 * Inherits From: [Previous/Next Buttons inherit from Swing Component JButton]
 * |+-----------------------------------------------------------------------
 */

class NextButton extends JButton {

	public NextButton() {
		super();
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		setContentAreaFilled(false);

	}

	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.gray);
		}
		int xPoints[] = { 0, 0, getWidth() };
		int yPoints[] = { 0, getHeight(), getHeight() / 2 };
		g.fillPolygon(xPoints, yPoints, xPoints.length);
		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(Color.black);
		int xPoints[] = { 0, 0, getWidth() };
		int yPoints[] = { 0, getHeight(), getHeight() / 2 };
		g.drawPolygon(xPoints, yPoints, xPoints.length);
	}

	Polygon polygon;

	public boolean contains(int x, int y) {
		if (polygon == null || !polygon.getBounds().equals(getBounds())) {
			int xPoints[] = { 0, 0, getWidth() };
			int yPoints[] = { 0, getHeight(), getHeight() / 2 };
			polygon = new Polygon(xPoints, yPoints, xPoints.length);
		}
		return polygon.contains(x, y);
	}

}

class PreviousButton extends JButton {
	public PreviousButton() {
		super();
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		setContentAreaFilled(false);

	}

	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.gray);
		}

		int xPoints[] = { getWidth(), getWidth(), 0 };
		int yPoints[] = { 0, getHeight(), getHeight() / 2 };
		g.fillPolygon(xPoints, yPoints, xPoints.length);
		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(Color.black);
		int xPoints[] = { getWidth(), getWidth(), 0 };
		int yPoints[] = { 0, getHeight(), getHeight() / 2 };
		g.drawPolygon(xPoints, yPoints, yPoints.length);
	}

	Polygon polygon;

	public boolean contains(int x, int y) {
		if (polygon == null || !polygon.getBounds().equals(getBounds())) {
			int xPoints[] = { getWidth(), getWidth(), 0 };
			int yPoints[] = { 0, getHeight(), getHeight() / 2 };
			polygon = new Polygon(xPoints, yPoints, xPoints.length);
		}
		return polygon.contains(x, y);
	}

} // END CLASS
