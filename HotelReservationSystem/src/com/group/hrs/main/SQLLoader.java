package com.group.hrs.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*+----------------------------------------------------------------------
||
||  Class [SQLLoader] 
||
||         Author:  [Lachlan McCallum]
||
||    Purpose:  [This class loads and parses a txt file located in res folder to 
||    set MYSQL database variables.  host user and password must be set for program
||    interact with MYSQL]
|+-----------------------------------------------------------------------*/

public class SQLLoader {

	// STATIC VARIABLES ACCESSIBLE TO DATABASE LOADER AND AUTHENTICATOR
	public static String host, user, password, database, sqlType;

	/********************************************
	 * loadSQLConfig Method : This method parses a text file and sets the static
	 * variables host, user and password
	 *******************************************/
	public static void loadSQLCongfig() {
		// buffered reader object initalized
		BufferedReader br = null;
		// string path to locate file
		String path = ("res/SQLconfig.txt");
		// create file from path
		File sqlFile = new File(path);

		try {

			br = new BufferedReader(new FileReader(sqlFile));
			// sets the variables by reading lines
			user = br.readLine();
			password = br.readLine();
			host = br.readLine();
			database = br.readLine();
			sqlType = br.readLine();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			SQLError();

		} catch (IOException e) {
			e.printStackTrace();
			SQLError();
		}

		try {
			// close buffered reader object
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/********************************************
	 * SQLError Method This method displays an error message if the file cannot
	 * be parsed due to not existing or formatting error
	 *******************************************/
	public static void SQLError() {
		JOptionPane.showMessageDialog(new JFrame(), "There is a problem with SQLConfig.txt located in /res folder.",
				"SQL CONFIGURATION ERROR", JOptionPane.ERROR_MESSAGE);
		System.exit(0);

	}

	public static String getSQLString() {

		return "jdbc:" + sqlType + "://" + host + "/" + database + "";

	}
	public static String getUserNamee(){
		return user;
	}
	public static String getPassword(){
		return password;
	}

}
