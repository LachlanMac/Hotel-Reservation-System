package com.group.hrs.main;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reservation {
	// Class variables
	public int rID;
	public int rRoomNumber;

	public String rFirstName;
	public String rLastName;
	public String rStreet;
	public String rCity;
	public String rState;
	public String rZipCode;
	public String rEmailAddress;
	public String rPhoneNumber;

	public String checkInDate;
	public String checkOutDate;

	public ArrayList<String> reservationDates;

	private Calendar cal;

	// Default Constructor
	public Reservation() {
		setResID(0);
		setRoom(0);
		setFirstName("");
		setLastName("");
		setStreet("");
		setCity("");
		setState("");
		setZip("");
		setEmail("");
		setPhone("");
		setCheckInDate("");
		setCheckOutDate("");
		cal = Calendar.getInstance();

	}

	// Overridden Constructor
	public Reservation(int id, int room, String first, String last, String street, String city, String state,
			String zip, String email, String phone, String inDate, String outDate) {
		setResID(id);
		setRoom(room);
		setFirstName(first);
		setLastName(last);
		setStreet(street);
		setCity(city);
		setState(state);
		setZip(zip);
		setEmail(email);
		setPhone(phone);
		setCheckInDate(inDate);
		setCheckOutDate(outDate);

	}

	public ArrayList<String> calculateDates(String checkInDate, String checkOutDate) {
		reservationDates = new ArrayList<String>();
		cal = Calendar.getInstance();
		Date checkIn = parseDate(checkInDate);
		Date checkOut = parseDate(checkOutDate);
		Date newDate = checkIn;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		// reservationDates.add(df.format(newDate));

		while (newDate.before(checkOut)) {

			reservationDates.add(df.format(newDate));

			newDate.setDate(newDate.getDate() + 1);

		}

		reservationDates.add(df.format(checkOut));

		return reservationDates;

	}

	public ArrayList<String> getReservationDates(){
		
		return this.reservationDates;
		
	}

	private Date parseDate(String date) {

		String[] tokens = date.split("-");
		int year = Integer.parseInt(tokens[0]);
		int month = Integer.parseInt(tokens[1]);
		int day = Integer.parseInt(tokens[2]);

		cal.set(year, month, day, 0, 0);
		return cal.getTime();

	}

	public String toString() {

		return this.rFirstName + " " + this.rLastName + this.rEmailAddress + this.rStreet + this.rCity + this.rState
				+ " " + this.checkInDate + " " + this.checkOutDate + " " + this.rRoomNumber;

	}

	/********************************************
	 * All setters for the variables *
	 *******************************************/

	public void setResID(int id) {
		rID = id;
	}

	public void setRoom(int room) {
		rRoomNumber = room;
	}

	public void setFirstName(String first) {
		rFirstName = first;
	}

	public void setLastName(String last) {
		rLastName = last;
	}

	public void setStreet(String street) {
		rStreet = street;
	}

	public void setCity(String city) {
		rCity = city;
	}

	public void setState(String state) {
		rState = state;
	}

	public void setZip(String zip) {
		rZipCode = zip;
	}

	public void setEmail(String email) {
		rEmailAddress = email;
	}

	public void setPhone(String phone) {
		rPhoneNumber = phone;
	}

	public void setCheckInDate(String inDate) {
		checkInDate = inDate;
	}

	public void setCheckOutDate(String outDate) {
		checkOutDate = outDate;
	}

	/********************************************
	 * All getters for the variables *
	 *******************************************/
	public int getID() {
		return rID;
	}

	public int getRoom() {
		return rRoomNumber;
	}

	public String getFirstName() {
		return rFirstName;
	}

	public String getLastName() {
		return rLastName;
	}

	public String getStreet() {
		return rStreet;
	}

	public String getCity() {
		return rCity;
	}

	public String getState() {
		return rState;
	}

	public String getZip() {
		return rZipCode;
	}

	public String getEmail() {
		return rEmailAddress;
	}

	public String getPhoneNumber() {
		return rPhoneNumber;
	}

	public String getInDate() {
		return checkInDate;
	}

	public String getOutDate() {
		return checkOutDate;
	}

}