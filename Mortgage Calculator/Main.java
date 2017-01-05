package mortgageCalc;

import javax.swing.JFrame;

import mortgageCalc.Frame;

public class Main {
	
	static Frame f;
	
	public static void main(String[] args) {
		f = new Frame();
		f.setTitle("Mortgage Calculator");
		f.setSize(800, 200);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.prince.requestFocusInWindow();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
	}
}
