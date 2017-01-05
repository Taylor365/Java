package taxCalc;

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
	public JLabel incLabel;
	public JLabel prsiLabel;
	public JLabel uscLabel;
	public JLabel outputLabel;
	public JLabel outputLabel2;	
	public JLabel outputLabel3;	
	public JLabel blankLabel;
	public JLabel blankLabel2;
	public JLabel salLabel;
		
	public JPanel menuButtonPanel;
	public JPanel userPanel;
	public JPanel userOutputPanel;
	
	public JLabel incTax;
	public JLabel prsi;
	public JLabel usc;
	
	public JButton calc;
	public JButton fileNew;
	public JButton fileExit;
	
	public double netSal;
	public double totalTax;
	
	public JTextField salary;
	
	public Frame(){
		
		menuButtonPanel = new JPanel();
		userPanel = new JPanel();
		
		layout = new GridLayout(0,2);
		userPanel.setLayout(layout);
		
		incLabel = new JLabel("Income Tax :");
		incTax = new JLabel();
		prsiLabel = new JLabel("PRSI :");
		prsi = new JLabel();
		uscLabel = new JLabel("USC :");
		usc = new JLabel();
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
	        
        salary = new JTextField(10);
        
        salLabel = new JLabel("Enter your yearly Salary: ");
        
        
        userPanel.add(salLabel);
        userPanel.add(salary);
        userPanel.add(incLabel);
        userPanel.add(incTax);
        userPanel.add(prsiLabel);
        userPanel.add(prsi);
        userPanel.add(uscLabel);
        userPanel.add(usc);
        userPanel.add(calc);  
        userPanel.add(outputLabel2);
        userPanel.add(blankLabel);
        userPanel.add(outputLabel);
        userPanel.add(blankLabel2);
        userPanel.add(outputLabel3);
        
        
        add(menuButtonPanel,BorderLayout.NORTH);
        add(userPanel,BorderLayout.CENTER);
		add(status, BorderLayout.SOUTH);
			
		fileNew.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to create a new calculation?") == 0){
					status.setText("Generated New Calculation");
					salary.setText("");
					incTax.setText(null);
					prsi.setText(null);
					usc.setText(null);
					outputLabel.setText(null);
					outputLabel2.setText(null);
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
				String salaryText = salary.getText();

				if(!salaryText.matches("[0-9]+")){
					outputLabel.setText("* Please enter your yearly salary in numerics.");
					salLabel.setText("Enter your yearly Salary: 	*");
					incTax.setText(null);
					prsi.setText(null);
					usc.setText(null);
					outputLabel2.setText(null);
					outputLabel3.setText(null);
				}else{
			        int salaryInt = Integer.parseInt(salaryText);
			        salLabel.setText("Enter your yearly Salary: ");
					
					taxCalculation(salaryInt);
					outputLabel.setText("Your net salary for 2017 is: " + "€" + NumberFormat.getNumberInstance().format(netSal));
					outputLabel3.setText("Your Monthly take home pay for 2017 is: " + "€" + NumberFormat.getNumberInstance().format((netSal / 12)));
					outputLabel2.setText("Your total tax paid for 2017 will be: " + "€" + NumberFormat.getNumberInstance().format(totalTax));
				}

				
			}
		});	
	}
		
	public void closeWindow(){
		WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(close);
	}
	
	public void taxCalculation(int wage){
		double prsiC = wage * 0.04;
		double uscC = 0;
		double incC = 0;
		double lowRate = 0.20;
		double highRate = 0.40;
		int taxCred = 3300;
		

		if(wage < 13000){
			prsiC = 0;
			totalTax = 0;
		}else if(wage > 13000 && wage < 18772){
			uscC = (12012 * 0.005) + ((wage - 12012) * 0.025);
			incC = (wage * lowRate) - taxCred;
			prsiC = 0;			
			if(incC < 0){
				incC = 0;
			}
				
		}else if(wage > 18772 && wage < 33800){
			uscC = (12012 * 0.005) + (6760 * 0.025) + ((wage - 18772) * 0.05);
			incC = (wage * lowRate) - taxCred;
			
		}else if(wage > 33800 && wage < 70044){
			uscC = (12012 * 0.005) + (6760 * 0.025) + ((wage - 18772) * 0.05);
			incC = (((33800 * lowRate) - taxCred) + ((wage - 33800) * highRate));			
			
		}else if(wage > 70044){
			uscC = (12012 * 0.005) + (6760 * 0.025) + (15028 * 0.05) + ((wage - 70044) * 0.08);
			incC = (((33800 * lowRate) - taxCred) + ((wage - 33800) * highRate));			
		}
		
		totalTax = incC + prsiC + uscC;
		
		incTax.setText(NumberFormat.getNumberInstance().format(incC));
		prsi.setText(NumberFormat.getNumberInstance().format(prsiC));
		usc.setText(NumberFormat.getNumberInstance().format(uscC));
		
		netSal = wage - totalTax;
		
			
	}
}
