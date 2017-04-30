package com.group.hrs.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//author Alexander Lyos
public class DatabaseLoader {

	// Serach database for ID and get all customer information
	private static String host = SQLLoader.getSQLString();
	private static String username = SQLLoader.getUserNamee();
	private static String password = SQLLoader.getPassword();

	public Reservation getReservationByID(int reservationID) throws SQLException {
		// Statement that connects to database
		Connection con = DriverManager.getConnection(host, username, password);

		// Variables
		int customerID = 0;
		int roomID = 0;

		// Variables that will be displayed
		int rID = 0;
		int roomNumber = 0;

		String firstname = "";
		String lastname = "";
		String street = "";
		String city = "";
		String state = "";
		String zipCode = "";
		String emailAddress = "";
		String phoneNumber = "";

		String checkInDate = "";
		String checkOutDate = "";

		// Statement that will search for reservation by reservationID
		PreparedStatement reservationStatement = con
				.prepareStatement("SELECT * FROM Reservation WHERE ReservationID = ?");
		reservationStatement.setInt(1, reservationID);
		ResultSet rs = reservationStatement.executeQuery();

		// Initialize variables
		while (rs.next()) {
			rID = rs.getInt("ReservationID");
			customerID = rs.getInt("CustomerID");
			roomID = rs.getInt("RoomID");
			checkInDate = rs.getString("StartDate");
			checkOutDate = rs.getString("EndDate");
		}

		// Statement that will search for room connected to the reservation
		PreparedStatement roomStatement = con.prepareStatement("SELECT * FROM Room WHERE RoomID = ?");
		roomStatement.setInt(1, roomID);
		ResultSet rs2 = roomStatement.executeQuery();

		// Initialize room number variable
		while (rs2.next()) {
			roomNumber = rs2.getInt("Room_Number");
		}

		// Statement that will search for customer connected to the reservation
		PreparedStatement customerStatement = con.prepareStatement("SELECT * FROM Customer WHERE CustomerID = ?");
		customerStatement.setInt(1, customerID);
		ResultSet rs3 = customerStatement.executeQuery();

		// Intialize firstname and lastname variables
		while (rs3.next()) {
			firstname = rs3.getString("Firstname");
			lastname = rs3.getString("Lastname");
			street = rs3.getString("Street");
			city = rs3.getString("City");
			state = rs3.getString("State");
			zipCode = rs3.getString("ZipCode");
			emailAddress = rs3.getString("EmailAddress");
			phoneNumber = rs3.getString("PhoneNumber");
		}

		Reservation data = new Reservation(rID, roomID, firstname, lastname, street, city, state, zipCode, emailAddress,
				phoneNumber, checkInDate, checkOutDate);
		return data;

	}

	public void deleteReservationByID(int reservationID) throws SQLException {
		// Statement that connects to database
		Connection con = DriverManager.getConnection(host, username, password);
		int customerID = 0;
		int roomID = 0;

		// Statement that will search for reservation by reservationID
		PreparedStatement reservationStatement = con
				.prepareStatement("SELECT * FROM Reservation WHERE ReservationID = ?");
		reservationStatement.setInt(1, reservationID);
		ResultSet rs = reservationStatement.executeQuery();

		while (rs.next()) {
			customerID = rs.getInt("CustomerID");
			roomID = rs.getInt("RoomID");
		}

		PreparedStatement deleteReservation = con.prepareStatement("DELETE FROM Reservation WHERE ReservationID = ?");
		deleteReservation.setInt(1, reservationID);
		deleteReservation.execute();

		PreparedStatement deleteRoomBindedToCustomer = con
				.prepareStatement("UPDATE Room SET CustomerID = null WHERE RoomID = ?");
		deleteRoomBindedToCustomer.setInt(1, roomID);
		deleteRoomBindedToCustomer.execute();

		PreparedStatement deleteCustomer = con.prepareStatement("DELETE FROM Customer WHERE CustomerID = ?");
		deleteCustomer.setInt(1, customerID);
		deleteCustomer.execute();
	}

	public ArrayList<Reservation> getResByName(String firstName, String lastName) throws SQLException {

		ArrayList<Reservation> resList = new ArrayList<Reservation>();

		Connection con = DriverManager.getConnection(host, username, password);

		PreparedStatement searchCustomer = con
				.prepareStatement("SELECT * FROM Customer WHERE Firstname = ? AND Lastname = ?");
		searchCustomer.setString(1, firstName);
		searchCustomer.setString(2, lastName);
		ResultSet rs = searchCustomer.executeQuery();
		int customerID = 0;
		int counter = 0;

		while (rs.next()) {
			Reservation r = new Reservation();

			customerID = rs.getInt("CustomerID");

			r.setFirstName(rs.getString("Firstname"));
			r.setLastName(rs.getString("Lastname"));
			r.setStreet(rs.getString("Street"));
			r.setCity(rs.getString("City"));
			r.setState(rs.getString("State"));
			r.setZip(rs.getString("ZipCode"));
			r.setEmail(rs.getString("EmailAddress"));
			r.setPhone(rs.getString("PhoneNumber"));

			PreparedStatement searchReservation = con
					.prepareStatement("SELECT * FROM Reservation WHERE CustomerID = ? ");
			searchReservation.setInt(1, customerID);
			ResultSet rs2 = searchReservation.executeQuery();

			while (rs2.next()) {

				r.setResID(rs2.getInt("ReservationID"));
				r.setRoom(rs2.getInt("RoomID"));
				r.setCheckInDate(rs2.getString("StartDate"));
				r.setCheckOutDate(rs2.getString("EndDate"));
			}

			resList.add(r);

		}

		return resList;

	}

	public ArrayList getReservationByName(String firstName, String lastName) throws SQLException {
		// Statement that connects to database
		Connection con = DriverManager.getConnection(host, username, password);
		Reservation reservation = new Reservation();

		// ArrayList to hold customers with same names
		ArrayList customerList = new ArrayList<>();

		// Variables
		int roomID = 0;
		int customerID = 0;

		// Variables that will be displayed
		int rID = 0;
		int roomNumber = 0;

		String firstname = "";
		String lastname = "";
		String street = "";
		String city = "";
		String state = "";
		String zipCode = "";
		String emailAddress = "";
		String phoneNumber = "";

		String checkInDate = "";
		String checkOutDate = "";

		PreparedStatement searchCustomer = con
				.prepareStatement("SELECT * FROM Customer WHERE Firstname = ? AND Lastname = ?");
		searchCustomer.setString(1, firstName);
		searchCustomer.setString(2, lastName);
		ResultSet rs = searchCustomer.executeQuery();

		// Initialize variables
		while (rs.next()) {
			customerID = rs.getInt("CustomerID");
			firstname = rs.getString("Firstname");
			lastname = rs.getString("Lastname");
			street = rs.getString("Street");
			city = rs.getString("City");
			state = rs.getString("State");
			zipCode = rs.getString("ZipCode");
			emailAddress = rs.getString("EmailAddress");
			phoneNumber = rs.getString("PhoneNumber");

			// Adds to the arraylist their firstname and lastname, which is
			// separated by a space
			customerList.add(customerID + " " + firstname + " " + lastname);
		}

		PreparedStatement searchReservation = con.prepareStatement("SELECT * FROM Reservation WHERE CustomerID = ? ");
		searchReservation.setInt(1, customerID);
		ResultSet rs2 = searchReservation.executeQuery();

		while (rs2.next()) {
			rID = rs2.getInt("ReservationID");
			roomID = rs2.getInt("RoomID");
			checkInDate = rs2.getString("StartDate");
			checkOutDate = rs2.getString("EndDate");
		}

		PreparedStatement searchRoom = con.prepareStatement("SELECT * FROM Room WHERE RoomID = ?");
		searchRoom.setInt(1, roomID);
		ResultSet rs3 = searchRoom.executeQuery();

		while (rs3.next()) {
			roomNumber = rs3.getInt("Room_Number");
		}

		// Returns the Arraylist of customers with identical names
		return customerList;
	}

	// Return an array that displays avaliable rooms for the selected date
	public boolean[] LookUpByDate(String date) throws SQLException {
		// Statement that connects to database
		Connection con = DriverManager.getConnection(host, username, password);

		int roomID = 0;
		int roomNumber = 0;
		ArrayList occupiedRooms = new ArrayList();
		boolean[] roomList = new boolean[9];

		for (int start = 1; start < roomList.length; start++) {
			roomList[start] = false;
		}

		// Get the roomID along with the date that it's occupied with
		PreparedStatement lookUpDate = con.prepareStatement(
				"SELECT RoomID FROM Reservation WHERE (Day1 = ?) OR (Day2 = ?) OR (Day3 = ?) OR (Day4 = ?) OR (Day5 = ?) OR (Day6 = ?) OR (Day7 = ?)");
		lookUpDate.setString(1, date);
		lookUpDate.setString(2, date);
		lookUpDate.setString(3, date);
		lookUpDate.setString(4, date);
		lookUpDate.setString(5, date);
		lookUpDate.setString(6, date);
		lookUpDate.setString(7, date);
		ResultSet rs = lookUpDate.executeQuery();

		// Initialize roomID
		while (rs.next()) {
			roomID = rs.getInt("RoomID");
			roomList[roomID] = true;
		}

		return roomList;
	}

	// Call the reservation's get methods and submit the data to database
	public void submitReservation(Reservation reservation) throws SQLException {
		int customerID = 0;
		int roomID = 0;

		// Statement that connects to database
		Connection con = DriverManager.getConnection(host, username, password);

		// Inserts customer to database
		PreparedStatement insertCustomerCommand = con.prepareStatement(
				"INSERT INTO Customer(Firstname, Lastname, Street, City, State, ZipCode, EmailAddress, PhoneNumber)"
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
		insertCustomerCommand.setString(1, reservation.getFirstName());
		insertCustomerCommand.setString(2, reservation.getLastName());
		insertCustomerCommand.setString(3, reservation.getStreet());
		insertCustomerCommand.setString(4, reservation.getCity());
		insertCustomerCommand.setString(5, reservation.getState());
		insertCustomerCommand.setString(6, reservation.getZip());
		insertCustomerCommand.setString(7, reservation.getEmail());
		insertCustomerCommand.setString(8, reservation.getPhoneNumber());
		insertCustomerCommand.execute();

		// After inserting the customer, grab the customerID to insert into the
		// Room table to ensure it is occupied by customer
		PreparedStatement searchCustomer = con
				.prepareStatement("SELECT CustomerID FROM Customer WHERE Firstname = ? AND Lastname = ?");
		searchCustomer.setString(1, reservation.getFirstName());
		searchCustomer.setString(2, reservation.getLastName());
		ResultSet rs = searchCustomer.executeQuery();
		while (rs.next()) {
			customerID = rs.getInt("CustomerID");
		}

		// Inserts CustomerID into Room table to ensure that room is occupied
		String insertOccupiedRoom = "UPDATE Room SET CustomerID = ? WHERE Room_Number = ?";
		PreparedStatement insertOccupiedRoomCommand = con.prepareStatement(insertOccupiedRoom);
		insertOccupiedRoomCommand.setInt(1, customerID);
		insertOccupiedRoomCommand.setInt(2, reservation.getRoom());
		insertOccupiedRoomCommand.executeUpdate();

		PreparedStatement searchRoom = con.prepareStatement("SELECT RoomID FROM Room WHERE Room_Number = ?");
		searchRoom.setInt(1, reservation.getRoom());
		ResultSet rs2 = searchRoom.executeQuery();
		while (rs2.next()) {
			roomID = rs2.getInt("RoomID");
		}

		// Inserts reservation information
		PreparedStatement insertReservationCommand = con.prepareStatement(
				"INSERT INTO Reservation(CustomerID, RoomID, StartDate, EndDate)" + "VALUES(?, ?, ?, ?)");
		insertReservationCommand.setInt(1, customerID);
		insertReservationCommand.setInt(2, roomID);
		insertReservationCommand.setString(3, reservation.getInDate());
		insertReservationCommand.setString(4, reservation.getOutDate());
		insertReservationCommand.execute();

		// Find the reservation by ID by looking up the information that was
		// just inserted
		int reservationID = 0;
		PreparedStatement findReservationID = con.prepareStatement(
				"SELECT ReservationID FROM Reservation WHERE (CustomerID = ?) AND (RoomID = ?) AND (StartDate = ?) AND (EndDate = ?)");
		findReservationID.setInt(1, customerID);
		findReservationID.setInt(2, roomID);
		findReservationID.setString(3, reservation.getInDate());
		findReservationID.setString(4, reservation.getOutDate());
		ResultSet findID = findReservationID.executeQuery();
		while (findID.next()) {
			reservationID = findID.getInt("ReservationID");
		}

		//
		ArrayList<String> getDates = new ArrayList<String>();
		getDates = reservation.calculateDates(reservation.getInDate(), reservation.getOutDate());

		for (String s : getDates) {

			System.out.println(s);
		}

		if (getDates.size() == 2) {
			PreparedStatement insertDays = con
					.prepareStatement("UPDATE Reservation SET Day1 = ?, Day2 = ? WHERE ReservationID = ?");
			insertDays.setString(1, getDates.get(0));
			insertDays.setString(2, getDates.get(1));
			insertDays.setInt(3, reservationID);
			insertDays.executeUpdate();
		} else if (getDates.size() == 3) {
			PreparedStatement insertDays = con
					.prepareStatement("UPDATE Reservation SET Day1 = ?, Day2 = ?, Day3 = ? WHERE ReservationID = ?");
			insertDays.setString(1, getDates.get(0));
			insertDays.setString(2, getDates.get(1));
			insertDays.setString(3, getDates.get(2));
			insertDays.setInt(4, reservationID);
			insertDays.executeUpdate();
		} else if (getDates.size() == 4) {
			PreparedStatement insertDays = con.prepareStatement(
					"UPDATE Reservation SET Day1 = ?, Day2 = ?, Day3 = ?, Day4 = ? WHERE ReservationID = ?");
			insertDays.setString(1, getDates.get(0));
			insertDays.setString(2, getDates.get(1));
			insertDays.setString(3, getDates.get(2));
			insertDays.setString(4, getDates.get(3));
			insertDays.setInt(5, reservationID);
			insertDays.executeUpdate();
		} else if (getDates.size() == 5) {
			PreparedStatement insertDays = con.prepareStatement(
					"UPDATE Reservation SET Day1 = ?, Day2 = ?, Day3 = ?, Day4 = ?, Day5 = ? WHERE ReservationID = ?");
			insertDays.setString(1, getDates.get(0));
			insertDays.setString(2, getDates.get(1));
			insertDays.setString(3, getDates.get(2));
			insertDays.setString(4, getDates.get(3));
			insertDays.setString(5, getDates.get(4));
			insertDays.setInt(6, reservationID);
			insertDays.executeUpdate();
		} else if (getDates.size() == 6) {
			PreparedStatement insertDays = con.prepareStatement(
					"UPDATE Reservation SET Day1 = ?, Day2 = ?, Day3 = ?, Day4 = ?, Day5 = ?, Day6 = ? WHERE ReservationID = ?");
			insertDays.setString(1, getDates.get(0));
			insertDays.setString(2, getDates.get(1));
			insertDays.setString(3, getDates.get(2));
			insertDays.setString(4, getDates.get(3));
			insertDays.setString(5, getDates.get(4));
			insertDays.setString(6, getDates.get(5));
			insertDays.setInt(7, reservationID);
			insertDays.executeUpdate();
		} else if (getDates.size() == 7) {
			PreparedStatement insertDays = con.prepareStatement(
					"UPDATE Reservation SET Day1 = ?, Day2 = ?, Day3 = ?, Day4 = ?, Day5 = ?, Day6 = ?, Day7 = ? WHERE ReservationID = ?");
			insertDays.setString(1, getDates.get(0));
			insertDays.setString(2, getDates.get(1));
			insertDays.setString(3, getDates.get(2));
			insertDays.setString(4, getDates.get(3));
			insertDays.setString(5, getDates.get(4));
			insertDays.setString(6, getDates.get(5));
			insertDays.setString(7, getDates.get(6));
			insertDays.setInt(8, reservationID);
			insertDays.executeUpdate();
		}

	}// end method

	public static void failedLoginAttempt(String employee) {
		try {

			int attempts = 0;
			Connection con = DriverManager.getConnection(host, username, password);

			PreparedStatement failedLogin = con
					.prepareStatement("SELECT LoginAttempts FROM Employee WHERE Username = ?");
			failedLogin.setString(1, employee);
			ResultSet rs = failedLogin.executeQuery();

			while (rs.next()) {

				attempts = rs.getInt("LoginAttempts");
				if (attempts >= 2) {

					PreparedStatement lockAccount = con
							.prepareStatement("UPDATE employee SET AccountLocked = ? WHERE Username = ?");
					lockAccount.setBoolean(1, true);
					lockAccount.setString(2, employee);
					lockAccount.executeUpdate();

				}

				PreparedStatement addAttempt = con
						.prepareStatement("UPDATE employee SET LoginAttempts = ? WHERE Username = ?");
				addAttempt.setString(2, employee);
				int temp = attempts + 1;
				addAttempt.setInt(1, temp);
				addAttempt.executeUpdate();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static boolean accountIsLocked(String employee) {
		try {
			Connection con = DriverManager.getConnection(host, username, password);

			PreparedStatement lockCheck = con.prepareStatement("SELECT * FROM Employee WHERE FirstName = ?");
			lockCheck.setString(1, employee);
			ResultSet rs = lockCheck.executeQuery();

			while (rs.next()) {
				if (rs.getBoolean(7) == true) {
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	public static void resetAttempts(String employee) {
		try {
			Connection con = DriverManager.getConnection(host, username, password);
			PreparedStatement resetAttempts = con
					.prepareStatement("UPDATE employee SET LoginAttempts = ? WHERE Username = ?");
			resetAttempts.setString(2, employee);
			resetAttempts.setInt(1, 0);
			resetAttempts.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
