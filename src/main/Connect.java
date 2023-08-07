package main;
import java.sql.*;

public class Connect {
	Connection con = null;
	public static Connection ConnectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MyBnB?enabledTLSProtocols=TLSv1.2", "root", "password");
			return con;
		} catch (Exception e) {
			return null;
		}
	}
}
