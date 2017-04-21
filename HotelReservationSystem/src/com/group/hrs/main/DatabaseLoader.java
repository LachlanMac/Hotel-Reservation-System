package com.group.hrs.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class DatabaseLoader{

    static //Serach database for ID and get all customer information
    String host = "jdbc:mysql://localhost:3306/HotelReservation";
    static String username = "root";
    static String password = "Movingon1";

    public Reservation getReservationByID(int reservationID) throws SQLException {
        //Statement that connects to database
        Connection con = DriverManager.getConnection(host, username, password);

        //Variables
        int customerID = 0;
        int roomID = 0;

        //Variables that will be displayed
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

        //Statement that will search for reservation by reservationID
        PreparedStatement reservationStatement = con.prepareStatement("SELECT * FROM Reservation WHERE ReservationID = ?");
        reservationStatement.setInt(1, reservationID);
        ResultSet rs = reservationStatement.executeQuery();

        //Initialize variables
        while (rs.next()) {
            rID = rs.getInt("ReservationID");
            customerID = rs.getInt("CustomerID");
            roomID = rs.getInt("RoomID");
            checkInDate = rs.getString("StartDate");
            checkOutDate = rs.getString("EndDate");
        }

        //Statement that will search for room connected to the reservation
        PreparedStatement roomStatement = con.prepareStatement("SELECT * FROM Room WHERE RoomID = ?");
        roomStatement.setInt(1, roomID);
        ResultSet rs2 = roomStatement.executeQuery();

        //Initialize room number variable
        while (rs2.next()) {
            roomNumber = rs2.getInt("Room_Number");
        }

        //Statement that will search for customer connected to the reservation
        PreparedStatement customerStatement = con.prepareStatement("SELECT * FROM Customer WHERE CustomerID = ?");
        customerStatement.setInt(1, customerID);
        ResultSet rs3 = customerStatement.executeQuery();

        //Intialize firstname and lastname variables
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

        Reservation data = new Reservation(rID, roomID, firstname, lastname, street, city, state, zipCode, emailAddress, phoneNumber, checkInDate, checkOutDate);
        return data;

    }

    //Return an array that displays avaliable rooms 
    public static void arrayReservation(String date) throws SQLException {
    	Connection con = DriverManager.getConnection(host, username, password);
    	PreparedStatement searchStatement = con.prepareStatement("SELECT * FROM Reservation WHERE StartDate = ?");
    	
    	
    	 ResultSet rs = searchStatement.executeQuery();
    	while(rs.next()){
    		
    		System.out.println(rs.getString("Room_Number"));
    		
    	}
    	
    	
    	
    }

    //Call the reservation's get methods and submit the data to database
    public void submitReservation(Reservation reservation) throws SQLException {
        int customerID = 0;
        int roomID = 0;
        
        //Statement that connects to database
        Connection con = DriverManager.getConnection(host, username, password);

        //Inserts customer to database
        PreparedStatement insertCustomerCommand = con.prepareStatement("INSERT INTO Customer(Firstname, Lastname, Street, City, State, ZipCode, EmailAddress, PhoneNumber)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        insertCustomerCommand.setString(1, reservation.getFirst());
        insertCustomerCommand.setString(2, reservation.getLast());
        insertCustomerCommand.setString(3, reservation.getStreet());
        insertCustomerCommand.setString(4, reservation.getCity());
        insertCustomerCommand.setString(5, reservation.getState());
        insertCustomerCommand.setString(6, reservation.getZip());
        insertCustomerCommand.setString(7, reservation.getEmail());
        insertCustomerCommand.setString(8, reservation.getPhoneNumber());
        insertCustomerCommand.execute();

        //After inserting the customer, grab the customerID to insert into the Room table to ensure it is occupied by customer
        PreparedStatement searchCustomer = con.prepareStatement("SELECT CustomerID FROM Customer WHERE Firstname = ? AND Lastname = ?");
        searchCustomer.setString(1, reservation.getFirst());
        searchCustomer.setString(2, reservation.getLast());
        ResultSet rs = searchCustomer.executeQuery();
        while(rs.next()){
            customerID = rs.getInt("CustomerID");
        }
         
        //Inserts CustomerID into Room table to ensure that room is occupied
        String insertOccupiedRoom = "UPDATE Room SET CustomerID = ? WHERE Room_Number = ?";
        PreparedStatement insertOccupiedRoomCommand = con.prepareStatement(insertOccupiedRoom);
        insertOccupiedRoomCommand.setInt(1, customerID);
        insertOccupiedRoomCommand.setInt(2, reservation.getRoom());
        insertOccupiedRoomCommand.executeUpdate();
        
        
        PreparedStatement searchRoom = con.prepareStatement("SELECT RoomID FROM Room WHERE Room_Number = ?");
        searchRoom.setInt(1, reservation.getRoom());
        ResultSet rs2 = searchRoom.executeQuery();
        while(rs2.next()){
            roomID = rs2.getInt("RoomID");
        }
        
        //Inserts reservation information
        PreparedStatement insertReservationCommand = con.prepareStatement("INSERT INTO Reservation(CustomerID, RoomID, StartDate, EndDate)"
                + "VALUES(?, ?, ?, ?)");
        insertReservationCommand.setInt(1, customerID);
        insertReservationCommand.setInt(2, roomID);
        insertReservationCommand.setString(3, reservation.getInDate());
        insertReservationCommand.setString(4, reservation.getOutDate());
        insertReservationCommand.execute();
    }

 

}