package stringProjects;

import java.util.Objects;
import java.util.Scanner;

//Check if a String is a Palindrome - spelled the same backwards as forwards.
public class Palindrome {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner (System.in);
		System.out.print("Enter a word: ");  
		String word = scanner.next();
		word = word.toLowerCase();
		String backword = new String();
		
		for (int i=word.length()-1; i>=0; i--) {
	        backword = backword + word.charAt(i);
	    }
		
		if(Objects.equals(word, backword)){
			System.out.println("This is a Palindrome!: " + backword);
		}
		else{
			System.out.println("This is not a Palindrome: " + backword);
		}
		
	    
	}
}
