package preprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

public class Custom_Tokenizer {
	
	String path = "tweet_list.txt";
	Stop_Word stopWord;
	ArrayList<String> listOfWord = new ArrayList<String>();
	
	public Custom_Tokenizer() {
		stopWord = new Stop_Word(); //Initalize list of stop words.
	}
	
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
				
				String[] wordList = sCurrentLine.split("\\s+");
				
				for (int i = 0; i < wordList.length; i++) {
					if (!stopWord.isStopWord(wordList[i])){
						listOfWord.add(wordList[i]);
					}
				}
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
	
	/**
	 * Get list of words that will be indexed.
	 * 
	 * @return
	 */
	public ArrayList<String> getListOfWord() {
		return listOfWord;
	}
}
