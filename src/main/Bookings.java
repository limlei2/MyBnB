package main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class Bookings {

	public static void createBooking(String username, int listingID, Date start, Date end) {
		int price = Listings.getListingPrice(listingID);
		try {
			Connection con = Connect.ConnectDB();
			String query = "INSERT INTO Bookings(listingID, booker, price, bookingstart, bookingend)"
			        + " VALUES (?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, listingID);
			preparedStmt.setString(2, username);
			preparedStmt.setInt(3, price);
			java.sql.Date sqlStartDate = new java.sql.Date(start.getTime());
			preparedStmt.setDate(4, sqlStartDate);
			java.sql.Date sqlEndDate = new java.sql.Date(end.getTime());
			preparedStmt.setDate(5, sqlEndDate);
			
			preparedStmt.execute();
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static boolean checkBookingDate(int listingID, Date start, Date end) {
		
		if(!Listings.checkListingDate(listingID,start,end)) {
			return false;
		}
		
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT bookingstart, bookingend FROM Bookings WHERE listingID = "+listingID+" AND cancelled = 0";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Date startdate = new Date(rs.getDate(1).getTime());
				Date enddate = new Date(rs.getDate(2).getTime());
				if(((startdate.before(start) || startdate.equals(start)) && enddate.after(start))
						|| (startdate.after(start) && startdate.before(end))){
					return false;
				}
			}
			return true;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public static int[] print10CurrentBookings(String username,int index) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT bookingID, address, postal, city, country, "
					+ "Bookings.price, bookingstart, bookingend FROM Bookings INNER JOIN Listings "
					+ "ON Bookings.listingID = Listings.listingID "
					+ "WHERE booker = '"+username+"' AND cancelled = 0";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(index>0 && rs.next()) {
				index--;
			}
			if(index>0) {
				System.out.println("\nNo more available listings!");
				return null;
			}
			int bookingIDs[] = new int[11];
			int i = 0;
			while(i<10 && rs.next()) {
				bookingIDs[i] = rs.getInt(1);
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
			bookingIDs[10] = i;
			return bookingIDs;
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public static void printBookingWithID(String username, int bookingID) {
		try {
			Connection con = Connect.ConnectDB();
			String query = "SELECT Listings.address, Listings.postal, Listings.city, Listings.country, "
					+ "Bookings.price, Bookings.bookingstart, Bookings.bookingend FROM Bookings INNER JOIN Listings "
					+ "ON Bookings.listingID = Listings.listingID "
					+ "WHERE booker = '"+username+"' AND bookingID = " + bookingID;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				String address = rs.getString(1);
				String postal = rs.getString(2);
				String city = rs.getString(3);
				String country = rs.getString(4);
				int price = rs.getInt(5);
				Date startdate = new Date (rs.getDate(6).getTime());
				Date enddate = new Date (rs.getDate(7).getTime());
				System.out.println("Listing info: "+address+", "+postal+", "+city+", "+country
						+ "\n\tPrice: $"+price+"\tStart Date: "+new java.sql.Date(startdate.getTime())+"\tEnd Date: "+new java.sql.Date(enddate.getTime()));
			}
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static void cancelBooking(String username, int bookingID) {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement st = con.prepareStatement("UPDATE Bookings SET cancelled = 1 WHERE booker = ? AND bookingID = ?");
		    st.setString(1, username);
		    st.setInt(2, bookingID);
		    st.executeUpdate();
			
			st.execute();
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
