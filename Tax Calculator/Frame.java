package taxCalc;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

import javax.swing.*;

public class Frame extends JFrame {
	
	//Create the layout to use.
	GridBagLayout layout = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	public double netSal;
	public double totalTax;
	
	JLabel incTax = new JLabel();
	JLabel prsi = new JLabel();
	JLabel usc = new JLabel();
	JTextField salary = new JTextField(10);

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
		JLabel incLabel = new JLabel("Income Tax :");
		JLabel prsiLabel = new JLabel("PRSI :");
		JLabel uscLabel = new JLabel("USC :");
		JLabel outputLabel = new JLabel();
		JLabel outputLabel2 = new JLabel();
		JLabel outputLabel3 = new JLabel();
		JLabel status = new JLabel("Welcome");
		JLabel salLabel = new JLabel("Enter your yearly Salary: ");
		
		//Buttons - used to invoke actions.
		JButton fileNew = new JButton("New");
		JButton fileExit = new JButton("Exit");
		JButton calc = new JButton("Calculate");
		
		menuButtonPanel.add(fileNew);
		menuButtonPanel.add(fileExit);
		
		//Plot out the positions for each object and add them to the User Panel.
		gbc.gridx = 0; //Horizontal position
		gbc.gridy = 0; //Vertical position
        	userPanel.add(salLabel, gbc);
        	gbc.gridx = 1;
		gbc.gridy = 0;
		userPanel.add(salary, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		userPanel.add(calc, gbc); 
		gbc.gridx = 0;
		gbc.gridy = 1;
		userPanel.add(incLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		userPanel.add(incTax, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		userPanel.add(prsiLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		userPanel.add(prsi, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		userPanel.add(uscLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		userPanel.add(usc, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		userPanel.add(outputLabel2, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
        	userPanel.add(outputLabel, gbc);
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
					salLabel.setText("Enter your yearly Salary: ");
					salary.setText("");
					incTax.setText(null);
					prsi.setText(null);
					usc.setText(null);
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
				String salaryText = salary.getText();
				
				//Check if the user entered the correct numeric input.
				if(!salaryText.matches("[0-9]+")){
					outputLabel.setText("* Please enter your yearly salary in numerics.");
					salLabel.setText("Enter your yearly Salary: *****");
					incTax.setText(null);
					prsi.setText(null);
					usc.setText(null);
					outputLabel2.setText(null);
					outputLabel3.setText(null);
					
					status.setText("Error in Calculation");
				}else{
					//Convert user input to Integer and insert into the Calculation method.
			        int salaryInt = Integer.parseInt(salaryText);
			        salLabel.setText("Enter your yearly Salary: ");
					
					taxCalculation(salaryInt);
					//Convert the output into number format(add in commas) and print to the User panel.
					outputLabel.setText("Your net salary for 2017 is: " + "€" + NumberFormat.getNumberInstance().format(netSal));
					outputLabel3.setText("Your Monthly take home pay for 2017 is: " + "€" + NumberFormat.getNumberInstance().format((netSal / 12)));
					outputLabel2.setText("Your total tax paid for 2017 will be: " + "€" + NumberFormat.getNumberInstance().format(totalTax));
					
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
	public void taxCalculation(int wage){
		double prsiC = wage * 0.04;
		double uscC = 0;
		double incC = 0;
		double lowRate = 0.20;
		double highRate = 0.40;
		int taxCred = 3300;
		
		//We check what tax bracket the salary entered falls into and adjust the calculation to fit.
		if(wage <= 13000){
			prsiC = 0;
			totalTax = 0;
		}else if(wage > 13000 && wage <= 18772){
			uscC = (12012 * 0.005) + ((wage - 12012) * 0.025);
			incC = (wage * lowRate) - taxCred;
			prsiC = 0;
			if(wage > 18304){
				prsiC = wage * 0.04;
			}
			if(incC < 0){
				incC = 0;
			}
				
		}else if(wage > 18772 && wage <= 33800){
			uscC = (12012 * 0.005) + (6760 * 0.025) + ((wage - 18772) * 0.05);
			incC = (wage * lowRate) - taxCred;
			
		}else if(wage > 33800 && wage <= 70044){
			uscC = (12012 * 0.005) + (6760 * 0.025) + ((wage - 18772) * 0.05);
			incC = (((33800 * lowRate) - taxCred) + ((wage - 33800) * highRate));			
			
		}else if(wage > 70044){
			uscC = (12012 * 0.005) + (6760 * 0.025) + (51271.99 * 0.05) + ((wage - 70044) * 0.08);
			incC = (((33800 * lowRate) - taxCred) + ((wage - 33800) * highRate));			
		}
		
		totalTax = incC + prsiC + uscC;
		
		//Setting the output labels for each tax. 
		incTax.setText(NumberFormat.getNumberInstance().format(incC));
		prsi.setText(NumberFormat.getNumberInstance().format(prsiC));
		usc.setText(NumberFormat.getNumberInstance().format(uscC));
		
		netSal = wage - totalTax;
	}
}
