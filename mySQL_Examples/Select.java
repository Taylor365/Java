package sqlExample;

import java.sql.*;

public class Select {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/test";
		String name = "root";
		String pw = "root";
		
		try {
			//Get a connection to DB:

			Connection con = DriverManager.getConnection(url, name, pw);
			
			//Create a statement:
			Statement state = con.createStatement();
			
			//Execute an SQL query:
			ResultSet res = state.executeQuery("SELECT * FROM customers");
			
			while(res.next()){
				System.out.println(res.getString("first_name") + " " + res.getString("last_name"));
			}
		
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		

	}

}
