package preprocessing;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileReader;
import java.io.BufferedReader;

public class Custom_Tokenizer {
	
	private static String path = "tweet_list.txt";
	
	public static void main(String [] args) {
		createVocabulary();
	}
	
	
	/**
	 * Create word bank from sample text file.
	 */
	public static void createVocabulary() {
		
		try (BufferedReader br = new BufferedReader(new FileReader(path)))
		{

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = removeURLFromString(sCurrentLine);
				sCurrentLine = removeNonAlphabetFromString(sCurrentLine);
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * Remove URL links from string
	 * 
	 * @param s
	 * @return
	 */
	private static String removeURLFromString(String string) {

        string = string.replaceAll("https?://\\S+\\s?", "");
        string = string.replaceAll("www.\\S+\\s?", "");
		return string;
	}
	
	/**
	 * Remove non alphabets from string.
	 * 
	 * @param string
	 * @return
	 */
	private static String removeNonAlphabetFromString(String string) {
		string = string.replaceAll("[^A-Za-z# ]", "");
		return string;
	}
}
