package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Reports {

	public static void totalBookingsCityAndDate(Date start, Date end) {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement ps = con.prepareStatement("SELECT Listings.city, COUNT(Bookings.bookingID) AS c"
		              + " FROM Bookings JOIN Listings WHERE Bookings.listingID = Listings.listingID AND"
		              + " Bookings.bookingstart >= ? AND Bookings.bookingend <= ?"
		              + " AND Bookings.cancelled = 0"
		              + " GROUP BY Listings.city ORDER BY c DESC");
			ps.setDate(1, new java.sql.Date(start.getTime()));
	        ps.setDate(2, new java.sql.Date(end.getTime()));
			ResultSet rs = ps.executeQuery();
			
			int i = 1;
			System.out.println("\n\nReport of Total Bookings by City From "+new java.sql.Date(start.getTime())+" till "+new java.sql.Date(end.getTime())+"\n");
			
			while(rs.next()) {
				System.out.println(i+": "+rs.getString(1) + "\tCount: "+rs.getInt(2));
				i++;
			}
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static void totalBookingsCityAndZipCodeAndDate(Date start, Date end) {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement ps = con.prepareStatement("SELECT Listings.city, Listings.postal, COUNT(Bookings.bookingID) AS c"
		              + " FROM Bookings JOIN Listings WHERE Bookings.listingID = Listings.listingID AND"
		              + " Bookings.bookingstart >= ? AND Bookings.bookingend <= ?"
		              + " AND Bookings.cancelled = 0"
		              + " GROUP BY Listings.city, Listings.postal ORDER BY c DESC");
			ps.setDate(1, new java.sql.Date(start.getTime()));
	        ps.setDate(2, new java.sql.Date(end.getTime()));
			ResultSet rs = ps.executeQuery();
			
			int i = 1;
			System.out.println("\n\nReport of Total Bookings by City And Postal From "+new java.sql.Date(start.getTime())+" till "+new java.sql.Date(end.getTime())+"\n");
			
			while(rs.next()) {
				System.out.println(i+": "+rs.getString(1) + ", "+rs.getString(2)+"\tCount: "+rs.getInt(3));
				i++;
			}
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static void totalListingsPerCountry() {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement ps = con.prepareStatement("SELECT country, COUNT(listingID) AS c"
		              + " FROM Listings GROUP BY country ORDER BY c DESC");
			ResultSet rs = ps.executeQuery();
			
			int i = 1;
			System.out.println("\n\nReport of Total Listings by Country");
			
			while(rs.next()) {
				System.out.println(i+": "+rs.getString(1) + "\tCount: "+rs.getInt(2));
				i++;
			}
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static void totalListingsPerCountryAndCity() {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement ps = con.prepareStatement("SELECT country, city, COUNT(listingID) AS c"
		              + " FROM Listings GROUP BY country, city ORDER BY c DESC");
			ResultSet rs = ps.executeQuery();
			
			int i = 1;
			System.out.println("\n\nReport of Total Listings by Country and City");
			
			while(rs.next()) {
				System.out.println(i+": "+rs.getString(1) + ", "+rs.getString(2)+"\tCount: "+rs.getInt(3));
				i++;
			}
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static void totalListingsPerCountryAndCityAndPostal() {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement ps = con.prepareStatement("SELECT country, city, postal, COUNT(listingID) AS c"
		              + " FROM Listings GROUP BY country, city, postal ORDER BY c DESC");
			ResultSet rs = ps.executeQuery();
			
			int i = 1;
			System.out.println("\n\nReport of Total Listings by Country, City and Postal Code");
			
			while(rs.next()) {
				System.out.println(i+": "+rs.getString(1) + ", "+rs.getString(2)+", "+rs.getString(3)+"\tCount: "+rs.getInt(4));
				i++;
			}
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static void mostCancellations() {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement ps = con.prepareStatement("SELECT booker, COUNT(booker) AS c"
		              + " FROM Bookings WHERE cancelled = 1 GROUP BY booker ORDER BY c DESC");
			ResultSet rs = ps.executeQuery();
			
			System.out.println("\n\nReport of Most Cancellations for Renters and Hosts");
			if(rs.next()) {
				System.out.println("\nRenter with the most cancellations: "+rs.getString(1)+"\t Number of Cancellations: "+rs.getInt(2));
			}
			
			PreparedStatement ps2 = con.prepareStatement("SELECT Listings.owner, COUNT(Listings.owner) AS c"
		              + " FROM Listings JOIN Bookings WHERE Listings.listingID = Bookings.listingID AND"
		              + " Bookings.cancelled = 1 GROUP BY Listings.owner ORDER BY c DESC");
			ResultSet rs2 = ps2.executeQuery();
			if(rs2.next()) {
				System.out.println("\nHost with the most cancellations: "+rs2.getString(1)+"\t Number of Cancellations: "+rs2.getInt(2));
			}
			
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static void rankRentersBookingsInTimePeriod(Date start, Date end) {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement ps = con.prepareStatement("SELECT booker, COUNT(booker) AS c"
		              + " FROM Bookings WHERE bookingstart >= ? AND bookingend <= ? GROUP BY booker ORDER BY c DESC");
			ps.setDate(1, new java.sql.Date(start.getTime()));
			ps.setDate(2, new java.sql.Date(end.getTime()));
			ResultSet rs = ps.executeQuery();
			
			int i = 1;
			System.out.println("\n\nReport of Ranking Renters Based on Bookings between the time period "+new java.sql.Date(start.getTime())+" till "+new java.sql.Date(end.getTime()));
			while(rs.next()) {
				System.out.println("\n"+i+": "+rs.getString(1)+"\t Number of Bookings: "+rs.getInt(2));
				i++;
			}
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static void rankHostsNumberOfListingsPerCountry() {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement ps = con.prepareStatement("SELECT owner, country, COUNT(listingID) AS c"
		              + " FROM Listings GROUP BY owner, country ORDER BY c DESC");
			ResultSet rs = ps.executeQuery();
			
			int i = 1;
			System.out.println("\n\nReport of Ranking Hosts Based on Listings Per Country");
			while(rs.next()) {
				System.out.println("\n"+i+": "+rs.getString(1)+"\t Country: "+rs.getString(2)+"\t Number of Listings: "+rs.getInt(3));
				i++;
			}
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public static void rankHostsNumberOfListingsPerCountryPerCity() {
		try {
			Connection con = Connect.ConnectDB();
			PreparedStatement ps = con.prepareStatement("SELECT owner, country, city, COUNT(listingID) AS c"
		              + " FROM Listings GROUP BY owner, country, city ORDER BY c DESC");
			ResultSet rs = ps.executeQuery();
			
			int i = 1;
			System.out.println("\n\nReport of Ranking Hosts Based on Listings Per Country");
			while(rs.next()) {
				System.out.println("\n"+i+": "+rs.getString(1)+"\t Country: "+rs.getString(2)+" City: "+rs.getString(3)+" Number of Listings: "+rs.getInt(4));
			}
			return;
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
}
