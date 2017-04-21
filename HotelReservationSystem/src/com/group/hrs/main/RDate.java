package com.group.hrs.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RDate extends JButton {

	private CalendarTool calendarTool;
	private boolean checkinSelected = false, checkoutSelected = false;
	private int day, month, year;
	private String display;
	private Calendar cal;
	private boolean[] reservations = new boolean[9];
	Date buttonDate;
	JFrame reservationDisplay;
	private final int MAX_RESERVATION_LENGTH = 7;

	public RDate(CalendarTool calendarTool, int day, int month, int year) {

		super();

		if (day == 0)
			display = "";
		else {
			display = Integer.toString(day);
		}

		this.setText(display);

		this.day = day;
		this.month = month;
		this.year = year;

		buttonDate = new Date();
		cal = Calendar.getInstance();
		cal.set(year, month, day);
		buttonDate = cal.getTime();

		this.calendarTool = calendarTool;
		this.setBackground(Color.WHITE);
		addListen();

	}

	public RDate(int day, int month, int year) {

		this.day = day;
		this.month = month;
		this.year = year;

		cal = Calendar.getInstance();
		cal.set(year, month, day);
		buttonDate = cal.getTime();

	}

	public void addListen() {

		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

			
				
				
				if (checkinSelected && calendarTool.getCheckoutDate() == null) {
					deselectCheckinDate();
					return;
				}
				if (checkoutSelected) {
					deselectCheckoutDate();
					return;
				}
				if (!checkinSelected) {
					if (calendarTool.getCheckinDate() == null) {

						selectCheckinDate();

					} else {
						if (calendarTool.getCheckoutDate() == null) {
							if (validCheckoutDate() && reservationTime()) {
								
								displayReservations();
								
								selectCheckoutDate();
							}
						}

					}
				}

			}

		});

	}

	public void displayReservations() {

		
		for(int i = 1; i < reservations.length; i++){
			
			
			reservations[i] = false;
			
		}
		reservations[3] = true;
		reservations[7] = true;
		calendarTool.drawReservationTracker(reservations, this);
		
	}

	public void selectCheckoutDate() {
		checkoutSelected = true;
		calendarTool.setCheckoutDate(this);
		this.setBackground(Color.blue);
		this.setOpaque(true);

		colorInbetween();

	}

	public void deselectCheckoutDate() {

		checkoutSelected = false;
		this.setBackground(Color.white);
		calendarTool.setCheckoutDate(null);

		unColorInbetween();
	}

	public void selectCheckinDate() {
		checkinSelected = true;
		calendarTool.setCheckinDate(this);
		this.setBackground(Color.red);
		this.setOpaque(true);

	}

	public void deselectCheckinDate() {
		checkinSelected = false;
		this.setBackground(Color.white);
		calendarTool.setCheckinDate(null);
	}

	public void softSelect() {
		this.setBackground(Color.PINK);
		this.setOpaque(true);
	}

	public void colorInbetween() {
		for (RDate jd : calendarTool.getDateList()) {
			if (jd.inbetweenDates()) {
				jd.setBackground(Color.pink);
			}

		}
	}

	public void unColorInbetween() {
		for (RDate jd : calendarTool.getDateList()) {
			if (!jd.equalsDate(calendarTool.getCheckinDate()) && !jd.equalsDate(calendarTool.getCheckoutDate())) {

				jd.setBackground(Color.white);
			}
		}
	}

	@Override
	public String toString() {

		// return buttonDate.toString();

		return Integer.toString(this.getYear()) + "-" + Integer.toString(this.getMonth()) + "-"
				+ Integer.toString(this.getDay());

	}

	public boolean validCheckoutDate() {

		if (calendarTool.getCheckinDate().getButtonDate().before(this.getButtonDate())) {

			return true;

		} else {
			return false;
		}

	}

	public boolean equalsDate(RDate date) {

		if (date == null)
			return false;

		if (this.getYear() == date.getYear() && this.getMonth() == date.getMonth() && this.getDay() == date.getDay()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean reservationTime() {

		cal.set(this.getYear(), this.getMonth(), this.getDay() - MAX_RESERVATION_LENGTH);

		if (cal.getTime().before(calendarTool.getCheckinDate().getButtonDate())) {
			cal.set(this.getYear(), this.getMonth(), this.getDay() + MAX_RESERVATION_LENGTH);
			return true;
		} else {
			cal.set(this.getYear(), this.getMonth(), this.getDay() + MAX_RESERVATION_LENGTH);

			JOptionPane.showMessageDialog(calendarTool, "Maximum Reservation number is 7 days");

			return false;
		}

	}

	public boolean inbetweenDates() {

		if (calendarTool.getCheckinDate().getButtonDate().before(this.getButtonDate())
				&& calendarTool.getCheckoutDate().getButtonDate().after(this.getButtonDate())) {
			return true;
		}

		else {
			return false;
		}

	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public Date getButtonDate() {
		return buttonDate;
	}
}
