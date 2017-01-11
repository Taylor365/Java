package mortgageCalc;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Frame extends JFrame {
	
	public GridLayout layout;
	
	public JLabel status;
	public JLabel princeLabel;
	public JLabel termLabel;
	public JLabel intLabel;
	public JLabel outputLabel;
	public JLabel outputLabel2;
	public JLabel outputLabel3;
	public JLabel blankLabel;	
	public JLabel blankLabel2;
	
	public JPanel menuButtonPanel;
	public JPanel userPanel;
	public JPanel userOutputPanel;
	
	public JTextField prince;
	public JTextField term;
	public JTextField interest;
	
	public JButton calc;
	public JButton fileNew;
	public JButton fileExit;
	
	public double total;
	public double totalInt;
	
	public Frame(){
		
		menuButtonPanel = new JPanel();
		userPanel = new JPanel();
		
		layout = new GridLayout(0,2);
		userPanel.setLayout(layout);
		
		princeLabel = new JLabel("Principle :");
		termLabel = new JLabel("Term of Mortgage :");
		intLabel = new JLabel("Interest Rate :");
		outputLabel = new JLabel();
		outputLabel2 = new JLabel();
		outputLabel3 = new JLabel();
		status = new JLabel("Welcome");
		blankLabel = new JLabel("");
		blankLabel2 = new JLabel("");
		
		fileNew = new JButton("New");
		fileExit = new JButton("Exit");
		calc = new JButton("Calculate");
		
		menuButtonPanel.add(fileNew);
		menuButtonPanel.add(fileExit);
	        
        prince = new JTextField(10);
        term = new JTextField(10);
        interest = new JTextField(3);
        
        userPanel.add(princeLabel);
        userPanel.add(prince);
        userPanel.add(termLabel);
        userPanel.add(term);
        userPanel.add(intLabel);
        userPanel.add(interest);
        userPanel.add(calc);  
        userPanel.add(outputLabel);
        userPanel.add(blankLabel);
        userPanel.add(outputLabel2);
        userPanel.add(blankLabel2);
        userPanel.add(outputLabel3);
        
        add(menuButtonPanel,BorderLayout.NORTH);
        add(userPanel,BorderLayout.CENTER);
		add(status, BorderLayout.SOUTH);
			
		fileNew.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to create a new calculation?") == 0){
					status.setText("Generated New Calculation");
					prince.setText("");
					term.setText("");
					interest.setText("");
					outputLabel.setText(null);
					outputLabel2.setText(null);
					outputLabel3.setText(null);
				}
			}
		});
		
		fileExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?") == 0){
					closeWindow();
				}
			}
		});	
		
		calc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String princeText = prince.getText();
		        String termText = term.getText();
		        String interText = interest.getText();
		        
		        if(!princeText.matches("[0-9]+") && !princeText.matches("[0-9]+") && !termText.matches("[0-9]+")){
					outputLabel.setText("* Please enter the principle of the mortgage in numerics.");
					outputLabel2.setText("* Please enter the term of the loan in years.");
					outputLabel3.setText("* Please enter the interest rate as a whole number or decimals (e.g. 4.5 = 4.5%).");
					
					princeLabel.setText("Principle :	*");
					termLabel.setText("Term of Mortgage :	*");
					intLabel.setText("Interest Rate :	*");
		        }else if(!princeText.matches("[0-9]+")){
					outputLabel.setText("* Please enter the principle of the mortgage in numerics.");
					princeLabel.setText("Principle :	*");
				}else if(!termText.matches("[0-9]+")){
					princeLabel.setText("Principle :");
					outputLabel.setText(null);
					
					outputLabel2.setText("* Please enter the term of the loan in years.");
					termLabel.setText("Term of Mortgage :	*");
				}else if(!interText.matches("[0-9]+.[0-9]+") && !interText.matches("[0-9]+")){
					termLabel.setText("Term of Mortgage :");
					outputLabel2.setText(null);
					
					outputLabel3.setText("* Please enter the interest rate as a whole number or decimals (e.g. 4.5 = 4.5%).");
					intLabel.setText("Interest Rate :	*");
				
				}else{
					int princeInt = Integer.parseInt(princeText);
					int termInt = Integer.parseInt(termText);
					double interInt = Double.parseDouble(interText);
					intLabel.setText("Interest Rate :");
					
					interestCalculation(princeInt, termInt, interInt);
					outputLabel.setText("<html>Your monthly repayments will be: " + "€" + NumberFormat.getNumberInstance().format(total));
					outputLabel2.setText("Your total interest paid for the loan will be: " + "€" + NumberFormat.getNumberInstance().format(totalInt));
					outputLabel3.setText("Total paid back to the bank: " + "€" + NumberFormat.getNumberInstance().format((totalInt + princeInt)));
				}
			}
		});	
	}
		
	public void closeWindow(){
		WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(close);
	}
	
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
		
		/*FOR TESTING
		System.out.println("This is the rate after calculation " + rate);
		System.out.println("This is the term after calculation " + length);
		System.out.println("This is the principle after calculation " + cost);
		System.out.println("This is the power calculation after calculation " + powercalc);
		System.out.println("Your monthly repayments will be: " + total);
		*/
	}
}
