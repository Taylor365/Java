package stockSim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Create {

	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/portfolio";
		String name = "root";
		String pw = "root";
		
		try {
			//Get a connection to DB:
			Connection con = DriverManager.getConnection(url, name, pw);
			
			//Create a statement:
			Statement state = con.createStatement();
			
			//Creating the portfolio table:
			String sql = "CREATE TABLE portfolio (" 
            + "idNum INT NOT NULL AUTO_INCREMENT,"  
            + "stocktick VARCHAR(255),"
            + "stock VARCHAR(255),"
            + "ask DOUBLE(10,2), "
            + "bid DOUBLE(10,2), "
            + "amount INT,"
            + "cost DOUBLE(10,2),"
            + "PRIMARY KEY(idNum))";
			
			state.executeUpdate(sql);
			
			//Creating the funds table:
			sql = "CREATE TABLE funds (" 
            + "initial DOUBLE(10,2), "
            + "current DOUBLE(10,2))";
			
			state.executeUpdate(sql);
			
			System.out.println("Tables Created");
			
			sql = "INSERT INTO funds (current, initial)"
					+ " VALUES (20000, 20000)";
					
			state.executeUpdate(sql);
			
			System.out.println("Insert Complete");
	
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
			

	}

}