package taxCalc;

import javax.swing.JFrame;

public class Main {
	
	static Frame f;
	
	public static void main(String[] args) {
		f = new Frame();
		f.setTitle("Tax Calculator");
		f.setSize(860, 300);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.salary.requestFocusInWindow();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
	}
}
