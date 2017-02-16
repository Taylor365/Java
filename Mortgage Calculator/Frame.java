package mortgageCalc;

import java.awt.*;
import java.awt.event.*;
import java.text.*;

import javax.swing.*;


public class Frame extends JFrame {
	
	//Create the layout to use.
	GridBagLayout layout = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	JTextField prince = new JTextField(10);
	
	public double total;
	public double totalInt;
	
	public Frame(){
		
		gbc.anchor = GridBagConstraints.WEST; //Aligns objects to the left.
		gbc.insets = new Insets(10, 10, 10, 10); //Inserts space between objects in each direction.
		
		//Create panels to hold objects.
		JPanel menuButtonPanel = new JPanel();
		JPanel userPanel = new JPanel();
		
		//userPanel will use the GridBagLayout.
		userPanel.setLayout(layout);
		
		//Create the objects for the panel.
		//Labels - used to print text to the frame.
		JLabel princeLabel = new JLabel("Principle :");
		JLabel termLabel = new JLabel("Term of Mortgage :");
		JLabel intLabel = new JLabel("Interest Rate :");
		JLabel outputLabel = new JLabel();
		JLabel outputLabel2 = new JLabel();
		JLabel outputLabel3 = new JLabel();
		JLabel status = new JLabel("Welcome");
		
		//Buttons - used to invoke actions.
		JButton fileNew = new JButton("New");
		JButton fileExit = new JButton("Exit");
		JButton calc = new JButton("Calculate");
		
		menuButtonPanel.add(fileNew);
		menuButtonPanel.add(fileExit);
	        
       		 //TextFields - used to accept user input or output text.
        	JTextField term = new JTextField(10);
        	JTextField interest = new JTextField(3);
        
        	//Plot out the positions for each object and add them to the User Panel.
		gbc.gridx = 0; //Horizontal position
		gbc.gridy = 0; //Vertical position
        	userPanel.add(princeLabel, gbc);
        	gbc.gridx = 1;
		gbc.gridy = 0;
        	userPanel.add(prince, gbc);
        	gbc.gridx = 0;
		gbc.gridy = 1;
        	userPanel.add(termLabel, gbc);
        	gbc.gridx = 1;
		gbc.gridy = 1;
        	userPanel.add(term, gbc);
        	gbc.gridx = 0;
		gbc.gridy = 2;
        	userPanel.add(intLabel, gbc);
        	gbc.gridx = 1;
		gbc.gridy = 2;
        	userPanel.add(interest, gbc);
        	gbc.gridx = 0;
		gbc.gridy = 3;
        	userPanel.add(calc, gbc);
        	gbc.gridx = 0;
		gbc.gridy = 4;
        	userPanel.add(outputLabel, gbc);
        	gbc.gridx = 0;
		gbc.gridy = 5;
        	userPanel.add(outputLabel2, gbc);
        	gbc.gridx = 0;
		gbc.gridy = 6;
        	userPanel.add(outputLabel3, gbc);
        
        	//Align each panel on the frame using BorderLayout.
        	add(menuButtonPanel,BorderLayout.NORTH);
        	add(userPanel,BorderLayout.CENTER);
		add(status, BorderLayout.SOUTH); //This will display the status after each new action.
		
		//Create ActionListener for each button.
		
		//This will reset everything for a new calculation.
		fileNew.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to create a new calculation?") == 0){
					status.setText("Generated New Calculation");
					princeLabel.setText("Principle :");
					termLabel.setText("Term of Mortgage :");
					intLabel.setText("Interest Rate :");
					prince.setText("");
					term.setText("");
					interest.setText("");
					outputLabel.setText(null);
					outputLabel2.setText(null);
					outputLabel3.setText(null);
				}
			}
		});		
		//This will close the application.
		fileExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?") == 0){
					closeWindow();
				}
			}
		});			
		//This will run the calculation using the user's input.
		calc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String princeText = prince.getText();
		        String termText = term.getText();
		        String interText = interest.getText();
		        
	        	//Check if the user entered the correct numeric input.	
		        //First we check all inputs, then cycle through each separately checking for numeric input.
		        if(!princeText.matches("[0-9]+") && !termText.matches("[0-9]+") && !interText.matches("[0-9]+")){
					outputLabel.setText("* Please enter the principle of the mortgage in numerics.");
					outputLabel2.setText("* Please enter the term of the loan in years.");
					outputLabel3.setText("* Please enter the interest rate as a whole number or decimals (e.g. 4.5 = 4.5%).");
					
					princeLabel.setText("Principle :	*");
					termLabel.setText("Term of Mortgage :	*");
					intLabel.setText("Interest Rate :	*");
					status.setText("Error in Calculation");
		        }else if(!princeText.matches("[0-9]+")){
					outputLabel.setText("* Please enter the principle of the mortgage in numerics.");
					princeLabel.setText("Principle :	*");
					status.setText("Error in Calculation");
		        }else if(!princeText.matches("[0-9]+")){
					outputLabel.setText("* Please enter the principle of the mortgage in numerics.");
					princeLabel.setText("Principle :	*");
					status.setText("Error in Calculation");
				}else if(!termText.matches("[0-9]+")){
					princeLabel.setText("Principle :");
					outputLabel.setText(null);
					outputLabel2.setText("* Please enter the term of the loan in years.");
					termLabel.setText("Term of Mortgage :	*");
					status.setText("Error in Calculation");
				}else if(!interText.matches("[0-9]+.[0-9]+") && !interText.matches("[0-9]+")){
					termLabel.setText("Term of Mortgage :");
					outputLabel.setText(null);
					outputLabel2.setText(null);
					outputLabel3.setText("* Please enter the interest rate as a whole number or decimals (e.g. 4.5 = 4.5%).");
					intLabel.setText("Interest Rate :	*");
					status.setText("Error in Calculation");
				}else{
					outputLabel.setText(null);
					outputLabel2.setText(null);
					outputLabel3.setText(null);
					
					//Convert JTextField to Integer/Double and insert into the Calculation method.
					int princeInt = Integer.parseInt(princeText);
					int termInt = Integer.parseInt(termText);
					double interDoub = Double.parseDouble(interText);
					intLabel.setText("Interest Rate :");
					
					interestCalculation(princeInt, termInt, interDoub);
					//Convert the output into number format(add in commas) and print to the User panel.
					outputLabel.setText("Your monthly repayments will be: " + "€" + NumberFormat.getNumberInstance().format(total));
					outputLabel2.setText("Your total interest paid for the loan will be: " + "€" + NumberFormat.getNumberInstance().format(totalInt));
					outputLabel3.setText("Total paid back to the bank: " + "€" + NumberFormat.getNumberInstance().format((totalInt + princeInt)));
					
					status.setText("Calculation Complete");
				}
			}
		});	
	}
	//The close window method.	
	public void closeWindow(){
		WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(close);
	}	
	//The Calculation method.
	public void interestCalculation(int cost, int length, double rate){
		rate = (rate/100)/12;
		double powercalc = 	(1 - (Math.pow((1 + rate), (-length * 12))));	
		
		total = (rate * cost) / powercalc;
		total = total * 100;
		total = Math.round(total);
		total = total / 100;		
		totalInt = (total * (length * 12) - cost);
		totalInt = totalInt * 100;
		totalInt = Math.round(totalInt);
		totalInt = totalInt / 100;		
	}
}
