package sqlExample;

import java.sql.*;

public class PreparedStatments {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/test";
		String name = "root";
		String pw = "root";
		PreparedStatement prepSt = null;
		
		
		try {
			//Get a connection to DB:

			Connection con = DriverManager.getConnection(url, name, pw);
			
			//Create a statement:
			prepSt = con.prepareStatement("SELECT * FROM employees WHERE emp_salary < ?");
			
			//Set the parameters:
			prepSt.setDouble(1, 50000);
			
			ResultSet res = prepSt.executeQuery();
			
			display(res);
			
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		

	}
	
	 private static void display(ResultSet res) throws SQLException { 
		 while (res.next()) { 
			 String firstName = res.getString("emp_firstname");
			 String lastName = res.getString("emp_lastname");
			 double salary = res.getDouble("emp_salary");
			 System.out.printf("%s, %s, %.2f\n", lastName, firstName, salary);
		}
	 }
		 
	 
}
