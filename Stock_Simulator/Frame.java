package stockSim;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;


import javax.swing.*;


public class Frame extends JFrame {
	
	//Create layout for each panel
	GridBagLayout layout = new GridBagLayout();
	GridBagConstraints gbcS = new GridBagConstraints();
	GridBagConstraints gbcP = new GridBagConstraints();
	GridBagConstraints gbcBuy = new GridBagConstraints();
	GridBagConstraints gbcSell = new GridBagConstraints();

	//Setting up DB connection
	String dbUrl = "jdbc:mysql://localhost:3306/portfolio";
	String name = "root";
	String pw = "root";
	PreparedStatement prepSt = null;
	//
	
	String[] words = new String[10];
	double amountInt = 0;
	double askInt;
	double loss = 18000;
	double profit = 22000;
	
	JLabel status = new JLabel("Welcome");
	JTextField stock = new JTextField(5);
	JPanel searchPanel = new JPanel();
	JButton bpAction = new JButton("Purchase Shares");
	JButton seAction = new JButton("Sell Shares");
	JTextField amount = new JTextField(5);
	
	//Setting up connection for retrieving stock info
	String nextLine;
    	URL url = null;
    	URLConnection urlConn = null;
    	InputStreamReader  inStream = null;
    	BufferedReader buff = null;
    	//
    
    	JLabel columnLabel = new JLabel("ID           || Stock Tick   ||Stock Name||       Buy        ||        Sell          ||     Amount     ||     Cost");
    	JLabel funds = new JLabel();
    	double current;
   	double cost;
    
    	//GAME PANEL
    	JPanel gamePanel = new JPanel();
    	JLabel gameOver = new JLabel("You have lost too much funds. The board are pulling your account! GAME OVER, man.");
    	JLabel gameOn = new JLabel("You have made a 10% return! The board are extatic and want to see you immediately! You're a real WINNER!");
    	//
    
	public Frame(){
		
		gbcS.anchor = GridBagConstraints.WEST;
		gbcS.insets = new Insets(10, 10, 10, 10);
		gbcBuy.anchor = GridBagConstraints.WEST;
		gbcBuy.insets = new Insets(10, 10, 10, 10);
		gbcSell.insets = new Insets(10, 10, 10, 10);
		
		//MENU PANEL	
		JPanel menuButtonPanel = new JPanel();
		JButton fileNew = new JButton("Search");
		JButton filePortfolio = new JButton("View Portfolio");
		JButton fileExit = new JButton("Exit");
   
		menuButtonPanel.add(fileNew);
		menuButtonPanel.add(filePortfolio);
		menuButtonPanel.add(fileExit);
		try {
			//Get a connection to DB:

			Connection con = DriverManager.getConnection(dbUrl, name, pw);
			
			//Create a statement:
			Statement state = con.createStatement();
			
			//Execute an SQL query:
			ResultSet res = state.executeQuery("SELECT current FROM funds");
			
			while(res.next()){
				current = res.getDouble("current");
			}
			
			res = state.executeQuery("SELECT * FROM portfolio");
			
			while(res.next()){
				cost = cost + res.getDouble("cost");
			}
			
			funds.setText("Funds Available: $" + NumberFormat.getNumberInstance().format(current) + "   Invested: $" + NumberFormat.getNumberInstance().format(cost));
			
			//Close connections.
	        	state.close();
	        	con.close();
	        
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		menuButtonPanel.add(funds);
		//

		//SEARCH PANEL
		JButton calc = new JButton("Get Stock Info");
		JLabel stockLabel = new JLabel("Stock Code:");
		JLabel askLabel = new JLabel("");
		JLabel bidLabel = new JLabel("");

		searchPanel.setLayout(layout); 
		gbcS.gridx = 0;
		gbcS.gridy = 0;
        	searchPanel.add(stockLabel, gbcS);
		gbcS.gridx = 1;
		gbcS.gridy = 0;
        	searchPanel.add(stock, gbcS);
		gbcS.gridx = 2;
		gbcS.gridy = 0;
        	searchPanel.add(calc, gbcS);
		gbcS.gridx = 0;
		gbcS.gridy = 3;
        	searchPanel.add(askLabel, gbcS);
		gbcS.gridx = 0;
		gbcS.gridy = 4;
        	searchPanel.add(bidLabel, gbcS);
        	//
        
        	//BUY PANEL
        	JPanel buyPanel = new JPanel();
        	buyPanel.setLayout(layout);
        	JButton buy = new JButton("Purchase");
        	JButton calcBuy = new JButton("Calculate Quote");
        	JLabel buyLabel = new JLabel("How many shares do you wish to buy? ");
        	JLabel confirmLabel = new JLabel("");        
		//
		
		//SELL PANEL
        	JPanel sellPanel = new JPanel();
        	sellPanel.setLayout(layout);
        	JButton sell = new JButton("Sell");
        	JLabel seStock = new JLabel();
        	//
        
        	//PORTFOLIO PANEL
        	JPanel portfolioPanel = new JPanel();
        	//
         
        	//Adding panels to the frame
        	add(menuButtonPanel,BorderLayout.NORTH);
        	add(searchPanel,BorderLayout.CENTER);
		add(status, BorderLayout.SOUTH);
			
		fileNew.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new search?") == 0){
					status.setText("Generated New Search");
					buyPanel.setVisible(false);
					sellPanel.setVisible(false);
					portfolioPanel.removeAll();
					portfolioPanel.setVisible(false);
					stock.setText(null);
					searchPanel.remove(bpAction);
					searchPanel.remove(seAction);
					askLabel.setText(null);
					bidLabel.setText(null);
					searchPanel.setVisible(true);

					gameState();
				}
			}
		});
		
		filePortfolio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				status.setText("Portfolio Retrieved");
				add(portfolioPanel,BorderLayout.CENTER);
				searchPanel.setVisible(false);
				buyPanel.setVisible(false);
				sellPanel.setVisible(false);
				
				ArrayList columnNames = new ArrayList();
		        	ArrayList data = new ArrayList();
		        
				try {
					//Get a connection to DB:

					Connection con = DriverManager.getConnection(dbUrl, name, pw);
						
					//Create a statement:
					Statement state = con.createStatement();
						
					//Execute an SQL query:
					ResultSet res = state.executeQuery("SELECT * FROM portfolio");
					
					ResultSetMetaData md = res.getMetaData();
		            		int columns = md.getColumnCount();

		            		//Get column names
		            		for(int i = 1; i <= columns; i++){
		                	columnNames.add(md.getColumnName(i));
		            		}
		     
		            		//Get row data
		            		while(res.next()){
		            	
						System.out.format("%s, %s, %s, %s, %s, %s\n",  res.getInt("idNum"), res.getString("stocktick"), res.getString("stock"), res.getDouble("ask"), res.getDouble("bid"), res.getInt("amount"), res.getDouble("cost"));
					
						ArrayList row = new ArrayList(columns);
						
						for(int i = 1; i <= columns; i++){
							row.add(res.getObject(i));
						}
						
						data.add(row);
		            		}
		            
			        	state.close();
			        	con.close();
		            
				} catch (SQLException ee) {
						
					ee.printStackTrace();
				}
				
				Vector columnNamesVector = new Vector();
		        	Vector dataVector = new Vector();

		        	for (int i = 0; i < data.size(); i++){
		            		ArrayList subArray = (ArrayList)data.get(i);
		            		Vector subVector = new Vector();
		            		for (int j = 0; j < subArray.size(); j++){
		                		subVector.add(subArray.get(j));
		            		}
		            		dataVector.add(subVector);
		        	}

		        	for (int i = 0; i < columnNames.size(); i++ ){
		            	columnNamesVector.add(columnNames.get(i));
		        	}


		        	//  Create table with database data    
		        	JTable table = new JTable(dataVector, columnNamesVector);
				gbcP.gridx = 0;
				gbcP.gridy = 0;
		        	portfolioPanel.add(columnLabel, gbcP);
				gbcP.gridx = 0;
				gbcP.gridy = 1;
		        	portfolioPanel.add(table, gbcP);
				portfolioPanel.setVisible(true);
			}
		});
		
		fileExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?") == 0){
					closeWindow();
				}
			}
		});	
		//Searches for stock within search panel and retrieves info and allows purchase or sale depending on ownership.
		calc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				seAction.setEnabled(false);
				bpAction.setEnabled(true);
				askLabel.setText(null);
				bidLabel.setText(null);
				stockSearch();
				askLabel.setText("Buy: " + words[1]);
				bidLabel.setText("Sell: " + words[2]);
				try {
					Connection con = DriverManager.getConnection(dbUrl, name, pw);
					Statement state = con.createStatement();
					ResultSet res = state.executeQuery("SELECT * FROM portfolio");
					
					while(res.next()){
						if(stock.getText().equals(res.getString("stocktick"))){
							seAction.setEnabled(true);
							bpAction.setEnabled(false);
						}
					}

			        state.close();
			        con.close();
			        
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				buyPanel.revalidate();
				buyPanel.remove(buy);
				sellPanel.revalidate();
				gameState();
			}
		});
		//MAKES BUY PANEL VISIBLE AND HIDES SEARCH PANEL
		bpAction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	
				searchPanel.setVisible(false);
				add(buyPanel,BorderLayout.CENTER);
				gbcBuy.gridx = 0;
				gbcBuy.gridy = 0;
				buyPanel.add(buyLabel, gbcBuy);
				gbcBuy.gridx = 1;
				gbcBuy.gridy = 0;
				buyPanel.add(amount, gbcBuy);
				gbcBuy.gridx = 2;
				gbcBuy.gridy = 0;
				buyPanel.add(calcBuy, gbcBuy);
				confirmLabel.setText(null);
				buyPanel.setVisible(true);
			}
		});
				
		seAction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//MAKES SELL PANEL VISIBLE AND HIDES SEARCH PANEL
				searchPanel.setVisible(false);
				add(sellPanel,BorderLayout.CENTER);
				double seAmount = 0;
				try {
					Connection con = DriverManager.getConnection(dbUrl, name, pw);
					Statement state = con.createStatement();
					ResultSet res = state.executeQuery("SELECT * FROM portfolio");
					
					while(res.next()){
						if(stock.getText().equals(res.getString("stocktick"))){
							seAmount = res.getDouble("amount");
						}
					}

			        	state.close();
			        	con.close();
			        
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
		        	seStock.setText("Sell " + words[0] + " at the bid price of: $" + words[2] + ". This will be worth a total of: $" + (seAmount * Double.parseDouble(words[2])));
				gbcSell.gridx = 0;
				gbcSell.gridy = 0;
		        	sellPanel.add(seStock, gbcSell);
				gbcSell.gridx = 0;
				gbcSell.gridy = 2;
		        	sellPanel.add(sell, gbcSell);
		        	sellPanel.setVisible(true);
			}
		});
		//CREATES QUOTE TO BUY. ADDS QUOTE AND CONFIRM TO BUY PANEL
		calcBuy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String amountText = amount.getText();
				if(!amountText.matches("[0-9]+")){
					confirmLabel.setText("* Please enter the amount of shares in numerics.");
					buyLabel.setText("How many shares do you wish to buy? *****");
					status.setText("Error in Calculation");
					buy.setEnabled(false);
				}else{
					buyLabel.setText("How many shares do you wish to buy? ");
					amountCalc(amount.getText());
					confirmLabel.setText("This purchase will cost you: $" + NumberFormat.getNumberInstance().format(amountInt) + ".");
					status.setText("");
					buy.setEnabled(true);
				}
				buyPanel.setVisible(false);
				gbcBuy.gridx = 0;
				gbcBuy.gridy = 1;
				buyPanel.add(confirmLabel, gbcBuy);
				gbcBuy.gridx = 2;
				gbcBuy.gridy = 1;
				buyPanel.add(buy, gbcBuy);
				buyPanel.setVisible(true);

			}
		});
		//CREATES TRANSACTION TO BUY AND RETURNS TO SEARCH PANEL.		
		buy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(current >= amountInt){
					stockPurchase(stock.getText(), amount.getText());	
					buyPanel.setVisible(false);
					searchPanel.setVisible(true);				
					askLabel.setText(null);
					bidLabel.setText(null);
					status.setText("Purchase Complete. You have bought $" + NumberFormat.getNumberInstance().format(amountInt) + " worth of " + words[0] + " shares.");
					stock.setText(null);
				}else{
					status.setText("You don't have enough funds. Please select a lower amount.");
				}
				
				//Clear panel for new purchase
				buyPanel.revalidate();
			}
		});
		//CREATES TRANSACTION TO SELL AND RETURNS TO SEARCH PANEL.
		sell.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				stockSold(stock.getText());
				sellPanel.setVisible(false);
				searchPanel.setVisible(true);
				askLabel.setText(null);
				bidLabel.setText(null);
				stock.setText(null);
				
				//Clear panel for new purchase
				sellPanel.repaint();
			}
		});
	}
		
	public void closeWindow(){
		WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(close);
	}
	//Calculating shares bought.
	private void amountCalc(String shares){
		askInt = Double.parseDouble(words[1]);
		amountInt = Integer.parseInt(shares);
		amountInt = amountInt * askInt;

	}
	//Calculating the final game state. If the player makes or loses 10% of the funds, this method is invoked.
	private void gameState(){
		add(gamePanel,BorderLayout.CENTER);
		
		if((cost + current) <= loss){
			System.out.println("The Game is Up.");
			gamePanel.add(gameOver);
			searchPanel.setVisible(false);
			gamePanel.setVisible(true);
		}else if((cost + current) >= profit){
			System.out.println("The Game is On!");
			gamePanel.add(gameOn);
			searchPanel.setVisible(false);
			gamePanel.setVisible(true);
		}

	}
	//Search method.
	public void stockSearch(){
	    try{
	       // Create the URL object that points at the default file index.html
	       url  = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + stock.getText() + "&f=nabd1");
	       urlConn = url.openConnection();
	       inStream = new InputStreamReader(urlConn.getInputStream());
	       buff= new BufferedReader(inStream);
	        
	       // Read and print the lines from the .csv
	       while (true){
	    	   nextLine =buff.readLine();
	           if (nextLine !=null){
	        	   System.out.println(nextLine);
	        	   nextLine = nextLine.replace(", ", " ");
	        	   System.out.println(nextLine);
           		   
            	   words = nextLine.split(",");

	               System.out.println(words.length);
	               System.out.println(words[0]);
	            }
	            else{
	               break;
	            } 
	        }
	       
	       } 
	    catch(MalformedURLException e1){
	    	System.out.println("Please check the URL:" + e1.toString() );
	    	}
	    catch(IOException  e2){
	    	System.out.println("Can't read  from the Internet: "+  e2.toString() );
	    	}
		gbcS.gridx = 2;
		gbcS.gridy = 3;
	    	searchPanel.add(bpAction, gbcS);
	    	gbcS.gridx = 2;
		gbcS.gridy = 4;
	    	searchPanel.add(seAction, gbcS);
	    	status.setText("Information Retrieved");

	}
	//Buying stock. An INSERT of retrieved details is made to the DB using prepared statements. A calculation is also made to update the funds.
	public void stockPurchase(String stockCode, String stockAmount){
		cost = 0;
		
		System.out.println(stockAmount);
		System.out.println(stockCode);
		System.out.println(words[0]);
		
		try {
			Connection con = DriverManager.getConnection(dbUrl, name, pw);
			Statement state = con.createStatement();
			prepSt = con.prepareStatement("INSERT INTO portfolio (stocktick, stock, ask, bid, amount, cost)"
					+ " VALUES (?, ?, ?, ?, ?, ?)");
			prepSt.setString(1, stockCode);
			prepSt.setString(2, words[0]);
			prepSt.setDouble(3, Double.parseDouble(words[1]));
			prepSt.setDouble(4, Double.parseDouble(words[2]));
			prepSt.setInt(5, Integer.parseInt(stockAmount));
			prepSt.setDouble(6, amountInt);
			
			prepSt.executeUpdate();
			
			System.out.println("Insert Complete");
			current = current - amountInt;
			
			prepSt = con.prepareStatement("UPDATE funds SET current = ?");
			prepSt.setDouble(1, current);
			
			prepSt.executeUpdate();
			
			System.out.println("Update Complete");
			
			ResultSet res = state.executeQuery("SELECT * FROM portfolio");
			
			while(res.next()){
				cost = cost + res.getDouble("cost");
			}
			
			funds.setText("Funds Available: $" + NumberFormat.getNumberInstance().format(current) + "   Invested: $" + NumberFormat.getNumberInstance().format(cost));
			
	        	prepSt.close();
	        	con.close();
		}
		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
	    	searchPanel.remove(bpAction);
	    	searchPanel.remove(seAction);
	}
	//Selling stock. The DB is contacted to retrieve the amount of stock bought for a calculation to update the funds and then the stock is removed from the DB.
	public void stockSold(String stockCode){	
		askInt = Double.parseDouble(words[2]);
		cost = 0;
		
		try {
			
			Connection con = DriverManager.getConnection(dbUrl, name, pw);
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT * FROM portfolio");
			while(res.next()){
				if(res.getString("stocktick").equals(stockCode)){
					int saleAmount = res.getInt("amount");
					amountInt = saleAmount;
					amountInt = amountInt * askInt;
				}
			}
			prepSt = con.prepareStatement("DELETE FROM portfolio WHERE stocktick = ?");
			prepSt.setString(1, stockCode);
			
			prepSt.executeUpdate();
			
			System.out.println("Deletion Complete");
			current = current + amountInt;
			
			prepSt = con.prepareStatement("UPDATE funds SET current = ?");
			prepSt.setDouble(1, current);
			
			prepSt.executeUpdate();
			
			System.out.println("Update Complete");
			
			res = state.executeQuery("SELECT * FROM portfolio");
			
			while(res.next()){
				cost = cost + res.getDouble("cost");
			}
			
			funds.setText("Funds Available: $" + NumberFormat.getNumberInstance().format(current) + "   Invested: $" + NumberFormat.getNumberInstance().format(cost));
			
			prepSt.close();
			state.close();
	        	con.close();
		} 
		
		catch (SQLException e) {
			
			e.printStackTrace();
		}

		status.setText("Sale Complete. You have sold $" + NumberFormat.getNumberInstance().format(amountInt) + " worth of " + stock.getText() + " shares.");
	    	searchPanel.remove(bpAction);
	    	searchPanel.remove(seAction);
	}
	
	
}
