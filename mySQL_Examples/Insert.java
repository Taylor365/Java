package sqlExample;

import java.sql.*;

public class Insert {

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
			String sql = "INSERT INTO customers (first_name, last_name, telephone, fullname)"
					+ " VALUES ('Jane', 'Mansfield', '0875555555', 'Jane Mansfield')";
			
			state.executeUpdate(sql);
			
			System.out.println("Insert Complete");
		
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		

	}

}