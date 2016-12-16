package stringProjects;

import java.util.Scanner;

//Take in a string and transpose the first letter to the end followed by an 'ay'
public class PigLatin {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner (System.in);
		System.out.print("Enter a word: ");  
		String word = scanner.next();
		String pigWord = new String();
		
		pigWord = word.substring(1, word.length()) + "-" + word.charAt(0) + "ay";
		
	    System.out.println(pigWord);
	}
}
