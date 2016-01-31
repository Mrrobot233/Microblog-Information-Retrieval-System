package preprocessing;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Custom_Tokenizer {
	
	String path = "tweet_list.txt";

	/**
	 * Create word bank from sample text file.
	 */
	public void createVocabulary() {
		
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
	public String removeURLFromString(String string) {

        string = string.replaceAll("https?://\\S+\\s?", ""); //Removes URL that begin with http
        string = string.replaceAll("www.\\S+\\s?", ""); //Removes URL that being with www
		return string;
	}
	
	/**
	 * Remove non alphabets from string.
	 * 
	 * @param string
	 * @return
	 */
	public String removeNonAlphabetFromString(String string) {
		string = string.replaceAll("[^A-Za-z# ]", ""); //Kept twitter hashtags... might be useful
		return string;
	}
}
