package stringProjects;

import java.util.Scanner;


//Take user input of a name and reverse it
public class StringReverse {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner (System.in);
		System.out.print("Enter your First Name: ");  
		String forward = scanner.next();
		String reverse = new String();
		
		for (int i=forward.length()-1; i>=0; i--) {
	        reverse = reverse + forward.charAt(i);
	    }
	    System.out.println(reverse);
	}
}
