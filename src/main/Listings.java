package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class Listings {

	public static void createListing(String type, String latitude, String longitude, String address,
			String postal, String city, String country, String characteristics, String owner,
			int price, Date start, Date end) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "INSERT INTO Listings(type, latitude, longitude, address, "
					+ "postal, city, country, characteristics, owner, price, startdate, enddate)"
			        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, type);
			preparedStmt.setString(2, latitude);
			preparedStmt.setString(3, longitude);
			preparedStmt.setString(4, address);
			preparedStmt.setString(5, postal);
			preparedStmt.setString(6, city);
			preparedStmt.setString(7, country);
			preparedStmt.setString(8, characteristics);
			preparedStmt.setString(9, owner);
			preparedStmt.setInt(10, price);
			preparedStmt.setDate(11, new java.sql.Date(start.getTime()));
			preparedStmt.setDate(12, new java.sql.Date(end.getTime()));
			
			preparedStmt.execute();
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void deleteListing(String username, int listingID) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "DELETE FROM Listings WHERE owner = '"+username+"'"
					+ "AND listingID = "+listingID;
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.execute();
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static boolean checkListing(String username, String address) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT * FROM Listings WHERE owner = '"+username+"' "
					+ "AND address = '"+address+"'";
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
	
	public static int[] filteredSearch10Listings(String s1, String s2, String type, int index) {
		String query = "";
		if(type.equals("longitude")) {
			query = "SELECT listingID, address, postal, city, country,"
					+ "price, startdate, enddate FROM Listings "
					+ "WHERE longitude = "+s1+" AND latitude = "+s2;
		} else {
			query = "SELECT listingID, address, postal, city, country,"
					+ "price, startdate, enddate FROM Listings "
					+ "WHERE "+type+" = '"+s1+"'";
		}
		try {
			Connection con = Connect.ConnectDB();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(index>0 && rs.next()) {
				index--;
			}
			if(index>0) {
				System.out.println("\nNo more available listings!");
				return null;
			}
			int[] listingIDs = new int[11];
			int i = 0;
			while(i<10 && rs.next()) {
				listingIDs[i] = rs.getInt(1);
				String address = rs.getString(2);
				String postal = rs.getString(3);
				String city = rs.getString(4);
				String country = rs.getString(5);
				int price = rs.getInt(6);
				Date startdate = rs.getDate(7);
				Date enddate = rs.getDate(8);
				int printind = i+1;
				System.out.println(printind+": "+address+", "+postal+", "+city+", "+country
						+ "\n\tPrice: $"+price+"\tStart Date: "+startdate+"\tEnd Date: "+enddate);
				i++;
			}
			listingIDs[10] = i;
			return listingIDs;
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public static int[] print10ListingsWithUsername(String username, int index) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT listingID, address, postal, city, country, "
					+ "price, startdate, enddate FROM Listings where owner = '"+username+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(index>0 && rs.next()) {
				index--;
			}
			if(index>0) {
				System.out.println("\nNo more available listings!");
				return null;
			}
			int[] listingIDs = new int[11];
			int i = 0;
			while(i<10 && rs.next()) {
				listingIDs[i] = rs.getInt(1);
				String address = rs.getString(2);
				String postal = rs.getString(3);
				String city = rs.getString(4);
				String country = rs.getString(5);
				int price = rs.getInt(6);
				Date startdate = rs.getDate(7);
				Date enddate = rs.getDate(8);
				int printind = i+1;
				System.out.println("\n"+printind+": "+address+", "+postal+", "+city+", "+country
						+ "\n\tPrice: $"+price+"\tStart Date: "+startdate+"\tEnd Date: "+enddate);
				i++;
			}
			listingIDs[10] = i;
			return listingIDs;
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public static int[] print10Listings(int index) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT listingID, address, postal, city, country, "
					+ "price, startdate, enddate FROM Listings";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(index>0 && rs.next()) {
				index--;
			}
			if(index>0) {
				System.out.println("\nNo more available listings!");
				return null;
			}
			int[] listingIDs = new int[11];
			int i = 0;
			while(i<10 && rs.next()) {
				listingIDs[i] = rs.getInt(1);
				String address = rs.getString(2);
				String postal = rs.getString(3);
				String city = rs.getString(4);
				String country = rs.getString(5);
				int price = rs.getInt(6);
				Date startdate = rs.getDate(7);
				Date enddate = rs.getDate(8);
				int printind = i+1;
				System.out.println(printind+": "+address+", "+postal+", "+city+", "+country
						+ "\n\tPrice: $"+price+"\tStart Date: "+startdate+"\tEnd Date: "+enddate);
				i++;
			}
			listingIDs[10] = i;
			return listingIDs;
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	
	public static void printListingWithID(int listingID) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT address, postal, city, country, "
					+ "price, startdate, enddate FROM Listings WHERE listingID = "+listingID;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				String address = rs.getString(1);
				String postal = rs.getString(2);
				String city = rs.getString(3);
				String country = rs.getString(4);
				int price = rs.getInt(5);
				Date startdate = rs.getDate(6);
				Date enddate = rs.getDate(7);
				System.out.println("\nListing details: "+address+", "+postal+", "+city+", "+country
						+ "\n\tPrice: $"+price+"\tStart Date: "+startdate+"\tEnd Date: "+enddate);
			}
			
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static int getListingPrice(int listingID) {
		int price = 0;
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT price FROM Listings WHERE listingID = "+listingID;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				price = rs.getInt(1);
			}
			return price;
		} catch(Exception e) {
			System.out.println(e);
			return price;
		}
	}
	
	public static boolean checkListingDate(int listingID, Date start, Date end) {
		try {
			Connection con = Connect.ConnectDB();
			String listingquery = "SELECT startdate, enddate FROM Listings WHERE listingID = "+listingID;
			Statement listingstmt = con.createStatement();
			ResultSet listrs = listingstmt.executeQuery(listingquery);
			if(!listrs.next()) {
				return false;
			}
			Date liststartdate = new Date(listrs.getDate(1).getTime());
			Date listenddate = new Date(listrs.getDate(2).getTime());
			
			if(liststartdate.after(start) || listenddate.before(end)) {
				return false;
			}
			return true;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
