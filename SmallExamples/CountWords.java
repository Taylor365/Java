package stringProjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

//Converts words from a text file into an array, prints each part of the array and counts how many words.
public class CountWords {
	public static void main(String[] args) throws IOException {
		String filename = "C:\\Users\\Carl\\Desktop\\file.txt";
		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    int numLines = 0;

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		        numLines++;
		    }
		    String everything = sb.toString();
		    String[] words = everything.split(" ");
		    
			int count = 0;
			
			for (int i = 0; i < words.length; i++) {
				System.out.println(words[i]);
				count++;
			}
			
			String vowels = "aeiou";
			String newWords = Arrays.toString(words); //Array converted into string to check for vowels
			int countV = 0;
			for(int i = 0;i < newWords.length();i++){
				for(int j = 0;j < vowels.length();j++){
					if(vowels.charAt(j) == newWords.charAt(i)){
						countV++;
						//System.out.println(vowels.charAt(j));
					}
				}
			}
			
			System.out.println("Number of words in file: " + count);
			System.out.println("Number of lines in file: " + numLines);
			System.out.println("Number of vowels in file: " + countV);
		    
		} finally {
		    br.close();
		}
		
	}
}
