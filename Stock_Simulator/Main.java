package stockSim;

import javax.swing.JFrame;

public class Main {
	
	static Frame f;
	
	public static void main(String[] args) {
		f = new Frame();
		f.setTitle("Stock Tracker");
		f.setSize(600, 400);
		f.setLocationRelativeTo(null);
		f.stock.requestFocusInWindow();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
