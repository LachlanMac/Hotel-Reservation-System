package com.group.hrs.main;

import java.sql.SQLException;

public class HRSLauncher {
	
	public static void main(String[] args) throws SQLException{
		
		
		//DatabaseLoader.arrayReservation("2017-4-20");
		
		LoginGUI login = new LoginGUI();
        login.showGUI();
        
        
	}
	

}
