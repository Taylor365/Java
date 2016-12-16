package stringProjects;

import java.util.Scanner;

//Count and print the vowels found within a user entered String
public class CountVowels {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner (System.in);
		System.out.print("Enter a word: ");  
		String word = scanner.next();
		String vowels = "aeiou";
		int count = 0;
		
		for(int i = 0;i < word.length();i++){
			for(int j = 0;j < vowels.length();j++){
				if(vowels.charAt(j) == word.charAt(i)){
					count++;
					System.out.println("There is a match - " + vowels.charAt(j));
				}
			}
		}
		
		
	    System.out.println("Vowels: " + count);
	    
	}
}
