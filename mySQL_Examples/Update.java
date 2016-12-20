package sqlExample;

import java.sql.*;

public class Update {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/test";
		String name = "root";
		String pw = "root";
		
		try {
			//Get a connection to DB:

			Connection con = DriverManager.getConnection(url, name, pw);
			
			//Create a statement:
			Statement state = con.createStatement();
			
			//Execute an SQL query: Update fullname with customers First and Last name. Remove the WHERE clause to have this query update globally.
			String sql = "UPDATE customers SET fullname = CONCAT(first_name, ' ', last_name)"
					+ " WHERE custID=1";
			
			int rowsAffected = state.executeUpdate(sql);
			
			System.out.println("Rows Affected " + rowsAffected);
			System.out.println("Update Complete");
		
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		

	}

}