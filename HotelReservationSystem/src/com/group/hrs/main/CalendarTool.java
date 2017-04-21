package com.group.hrs.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarTool extends JFrame {

	private final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;

	JFrame rShower;
	JPanel rPanel;

	private RDate checkinDate, checkoutDate;

	private JPanel datePanel, uiPanel, mfPanel;
	private JLabel dateDisplay;

	private PreviousButton previousButton;
	private NextButton nextButton;

	private Date date;
	private Calendar calendar;
	private DateFormat df = new SimpleDateFormat("MMM - yyyy");

	private ArrayList<RDate> dateList;

	private int currentYear, currentMonth, daysInMonth, firstDayOfMonth;

	private RForm form;

	public CalendarTool(RForm form) {
		this.form = form;
		dateList = new ArrayList<RDate>();
		date = new Date();
		calendar = Calendar.getInstance();
		currentYear = calendar.get(Calendar.YEAR);
		currentMonth = calendar.get(Calendar.MONTH);
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

		date = calendar.getTime();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {

				dispose();

			}
		});

		setTitle("Calendar Tool");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

		buildDisplay();

		buildMonth();

	}

	public void drawReservationTracker(boolean[] rArray, RDate rDate) {

		Dimension buttonSize = new Dimension(100, 100);
		
		if (rShower != null) {
			rShower.dispose();
		}

		rShower = new JFrame();
		rShower.setTitle("Reservations for date : " + rDate.getButtonDate().toString());
		rShower.setSize(new Dimension(400, 400));
		rShower.setPreferredSize(new Dimension(400, 400));
		rShower.setLocation(0, this.getY());

		rPanel = new JPanel();
		rShower.setLayout(new GridLayout(8, 0));

		//rShower.add(rPanel);

		
		
		

		
		
		
		JButton room_1 = new JButton("Room 1");
		room_1.setPreferredSize(buttonSize);
		if (rArray[1] == false) {
			room_1.setBackground(Color.green);
			room_1.setEnabled(true);
		} else {
			room_1.setBackground(Color.red);
			room_1.setEnabled(false);
		}
		
		JButton room_2 = new JButton("Room 2");
		room_2.setPreferredSize(buttonSize);
		if (rArray[2] == false) {
			room_2.setBackground(Color.green);
			room_2.setEnabled(true);
		} else {
			room_2.setBackground(Color.red);
			room_2.setEnabled(false);
		}
		
		JButton room_3 = new JButton("Room 3");
		room_3.setPreferredSize(buttonSize);
		if (rArray[3] == false) {
			room_3.setBackground(Color.green);
			room_3.setEnabled(true);
		} else {
			room_3.setBackground(Color.red);
			room_3.setEnabled(false);
		}
		
		JButton room_4 = new JButton("Room 4");
		room_4.setPreferredSize(buttonSize);
		if (rArray[4] == false) {
			room_4.setBackground(Color.green);
			room_4.setEnabled(true);
		} else {
			room_4.setBackground(Color.red);
			room_4.setEnabled(false);
		}
		
		JButton room_5 = new JButton("Room 5");
		room_5.setPreferredSize(buttonSize);
		if (rArray[5] == false) {
			room_5.setBackground(Color.green);
			room_5.setEnabled(true);
		} else {
			room_5.setBackground(Color.red);
			room_5.setEnabled(false);
		}
		
		JButton room_6 = new JButton("Room 6");
		room_6.setPreferredSize(buttonSize);
		if (rArray[6] == false) {
			room_6.setBackground(Color.green);
			room_6.setEnabled(true);
		} else {
			room_6.setBackground(Color.red);
			room_6.setEnabled(false);
		}
		
		JButton room_7 = new JButton("Room 7");
		room_7.setPreferredSize(buttonSize);
		if (rArray[7] == false) {
			room_7.setBackground(Color.green);
			room_7.setEnabled(true);
		} else {
			room_7.setBackground(Color.red);
			room_7.setEnabled(false);
		}
		
		JButton room_8 = new JButton("Room 8");
		room_8.setPreferredSize(buttonSize);
		if (rArray[8] == false) {
			room_8.setBackground(Color.green);
			room_8.setEnabled(true);
		} else {
			room_8.setBackground(Color.red);
			room_8.setEnabled(false);
		}
		
		
		roomListener(room_1, 1);
		roomListener(room_2, 2);
		roomListener(room_3, 3);
		roomListener(room_4, 4);
		roomListener(room_5, 5);
		roomListener(room_6, 6);
		roomListener(room_7, 7);
		roomListener(room_8, 8);
		
		
		rShower.add(room_1);
		rShower.add(room_2);
		rShower.add(room_3);
		rShower.add(room_4);
		rShower.add(room_5);
		rShower.add(room_6);
		rShower.add(room_7);
		rShower.add(room_8);

		rShower.pack();
		rShower.setVisible(true);

	}

	public void roomListener(JButton button, int ID){
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				form.setRoomNumber(Integer.toString(ID));
				rShower.dispose();
				
			}
			
			
			
			
			
		});
		
		
		
		
	}
	
	public void updateCalendar(int newMonth) {

		currentMonth = newMonth + currentMonth;
		calendar.set(Calendar.MONTH, currentMonth);
		currentYear = calendar.get(Calendar.YEAR);
		currentMonth = calendar.get(Calendar.MONTH);
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

		date = calendar.getTime();

		buildMonth();

	}

	public void setCheckinDate(RDate date) {

		this.checkinDate = date;
		if (date != null)
			form.setCheckinDate(checkinDate.toString());
		if (checkinDate == null) {
			form.setCheckinDate("");
		}
	}

	public RDate getCheckinDate() {

		return checkinDate;

	}

	public void setCheckoutDate(RDate date) {

		this.checkoutDate = date;
		if (date != null)
			form.setCheckoutDate(checkoutDate.toString());
		if (checkoutDate == null) {
			form.setCheckoutDate("");
		}
	}

	public RDate getCheckoutDate() {
		return checkoutDate;

	}

	public void buildDisplay() {

		mfPanel = new JPanel();
		mfPanel.setLayout(new GridLayout(0, 7));

		uiPanel = new JPanel();
		uiPanel.setLayout(new FlowLayout());

		nextButton = new NextButton();
		previousButton = new PreviousButton();
		dateDisplay = new JLabel();

		dateDisplay.setText(df.format(date));

		previousButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				updateCalendar(-1);
				dateDisplay.setText(df.format(date));

			}

		});

		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				updateCalendar(1);
				dateDisplay.setText(df.format(date));
			}
		});

		this.add(mfPanel, BorderLayout.PAGE_START);
		this.add(uiPanel, BorderLayout.PAGE_END);

		uiPanel.add(previousButton);
		uiPanel.add(dateDisplay);
		uiPanel.add(nextButton);

		mfPanel.add(new JButton("Sunday")).setEnabled(false);
		mfPanel.add(new JButton("Monday")).setEnabled(false);
		mfPanel.add(new JButton("Tuesday")).setEnabled(false);
		mfPanel.add(new JButton("Wednesday")).setEnabled(false);
		mfPanel.add(new JButton("Thursday")).setEnabled(false);
		mfPanel.add(new JButton("Friday")).setEnabled(false);
		mfPanel.add(new JButton("Saturday")).setEnabled(false);
	}

	public void buildMonth() {

		if (datePanel != null) {
			this.remove(datePanel);
		}
		datePanel = new JPanel();
		datePanel.setLayout(new GridLayout(0, 7));

		this.add(datePanel, BorderLayout.CENTER);

		for (int j = 1; j < firstDayOfMonth; j++) {

			datePanel.add(new JButton("")).setEnabled(false);

		}

		for (int i = 1; i <= daysInMonth; i++) {
			RDate d = new RDate(this, i, currentMonth, currentYear);

			dateList.add(d);
			datePanel.add(d);
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

		this.pack();

	}

	public ArrayList<RDate> getDateList() {

		return dateList;
	}

	public RForm getForm() {
		return form;
	}

}

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

}
