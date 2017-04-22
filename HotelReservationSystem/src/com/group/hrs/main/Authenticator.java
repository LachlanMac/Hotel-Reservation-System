package com.group.hrs.main;

import java.sql.*;

public class Authenticator {

	static Connection con;

	public static boolean validate(String username, String password) {
		boolean status = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + SQLLoader.host + "/HotelReservation?" + "user="
					+ SQLLoader.user + "&password=" + SQLLoader.password + "&useSSL=false");
			PreparedStatement ps = con.prepareStatement("select * from employee where username=? and password=?");
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			status = rs.next();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return status;
	}

}