package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class User {
	
	public static void createUser(String username, String password, String DOB, 
			String address, String occupation, String SIN, String first, String last, int host) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "INSERT INTO Users"
			        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, password);
			preparedStmt.setString(3, DOB);
			preparedStmt.setString(4, address);
			preparedStmt.setString(5, occupation);
			preparedStmt.setString(6, SIN);
			preparedStmt.setString(7, first);
			preparedStmt.setString(8, last);
			preparedStmt.setInt(9, host);
			
			preparedStmt.execute();
			con.close();
			
			System.out.println("New User Created!");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static boolean checkUser(String username) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT * FROM Users WHERE username = '"+username+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return true;
			}
			return false;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public static void deleteUser(String username) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "DELETE FROM Users WHERE username = '"+username+"'";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.execute();
			con.close();
			System.out.println("User deleted");

		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static int login(String username, String password) { // 0 means incorrect, 1 means renter, 2 means host
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT host FROM Users WHERE username = '"+username+"' AND password = '"+password+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(!rs.next()) {
				return 0;
			}
			int host = rs.getInt(1);
			if(host == 0) {
				return 1;
			}
			return 2;
		} catch(Exception e) {
			System.out.println(e);
			return 0;
		}
	}
}
