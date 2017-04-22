package com.group.hrs.main;

import java.sql.SQLException;

public class HRSLauncher {
	
	public static void main(String[] args) throws SQLException{
		
		
		SQLLoader.loadSQLCongfig();
		
		LoginGUI login = new LoginGUI();
        login.showGUI();
        
        
	}
	

}
