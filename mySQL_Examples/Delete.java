package sqlExample;

import java.sql.*;

public class Delete {

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
			String sql = "DELETE FROM customers WHERE last_name='Mansfield'";
			
			int rowsAffected = state.executeUpdate(sql);
			
			System.out.println("Rows Affected " + rowsAffected);
			System.out.println("Deletion Complete");
		
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		

	}

}