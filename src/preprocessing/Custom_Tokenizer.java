package preprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Custom_Tokenizer {
	
	private String filtered_tweet_list_path = "filtered_tweet_list.txt"; //Document containing filtered tweets.
	private String non_filtered_tweet_list_path = "non_filtered_tweet_list.txt"; //Document containing non-filtered tweets.
	private Stop_Word stopWord;

	//List of filtered tweets without stop words, numbers, urls and punctuation.
	private ArrayList<String> listOfFilteredTweet = new ArrayList<String>(); 
	private HashMap<Integer, String> listOfTweetId = new HashMap<Integer, String>();
	
	public Custom_Tokenizer() {
		stopWord = new Stop_Word(); //Initalize list of stop words.
	}
	
	/**
	 * Creates a list of tweetIds that will be used for displaying results when matching tweets to queries.
	 * 
	 * ArrayList index implies document number.
	 */
	public void createTweetIdReference() {
		try (BufferedReader br = new BufferedReader(new FileReader(non_filtered_tweet_list_path))) 
		{
			int documentCount = 0;
			listOfTweetId.clear();
			String sCurrentLine;
			
			System.out.println("Creating list of tweet IDs.");
			
			try {
				while ((sCurrentLine = br.readLine()) != null) {
					documentCount++;
					listOfTweetId.put(documentCount, sCurrentLine.split("	")[0]);
				}
			} finally {
				br.close();
		    }
			
			System.out.println("Finished creatomg list of tweet IDs.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates list of filtered tweets from filtered documented.
	 * 
	 * This may not exist.  To create a filtered document, please use
	 * createFilteredListOfTweetFromNonFilteredDocument followed by calling
	 * addFilteredTweetToFilteredDocument.
	 */
	public void createFilteredListOfTweetFromFilteredDocument() {
		try (BufferedReader br = new BufferedReader(new FileReader(filtered_tweet_list_path))) 
		{
			listOfFilteredTweet.clear();
			String sCurrentLine;
			
			System.out.println("Creating list of filtered tweets.");
			
			try {
				while ((sCurrentLine = br.readLine()) != null) {
					listOfFilteredTweet.add(sCurrentLine);
				}
			} finally {
				br.close();
		    }
			
			System.out.println("Finished list of filtered tweets.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates list of filtered tweets from non filtered document.
	 */
	public void createFilteredListOfTweetFromNonFilteredDocument() {
		
		try (BufferedReader br = new BufferedReader(new FileReader(non_filtered_tweet_list_path)))
		{
			String sCurrentLine;

			System.out.println("Creating list of filtered tweets.");
			
			try {
				while ((sCurrentLine = br.readLine()) != null) {
					sCurrentLine = removeURLFromString(sCurrentLine);
					sCurrentLine = removeNonAlphabetFromString(sCurrentLine);
					
					String filteredTweet = "";
					String[] wordList = sCurrentLine.split("\\s+");
					
					for (int i = 0; i < wordList.length; i++) {
						if (!stopWord.isStopWord(wordList[i])){
							filteredTweet += wordList[i] + " ";
						}
					}
					filteredTweet = filteredTweet.trim();
					listOfFilteredTweet.add(filteredTweet);
				}
			} finally {
				br.close();
		    }
			
			System.out.println("Finished list of filtered tweets.");

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
	 * Adds filtered tweets to document containing filtered tweets.
	 */
	public void addFilteredTweetToFilteredDocument() {
		try {

			System.out.println("Adding list of filtered tweets to filtered document.");
			
			BufferedWriter out = new BufferedWriter(new FileWriter(filtered_tweet_list_path, false));
			for (int i = 0; i < listOfFilteredTweet.size(); i++) {
				out.write(listOfFilteredTweet.get(i));
				out.newLine();
			}
			
			out.close();

			System.out.println("Completed adding list of filtered tweets to filtered document.");
			
		} catch (IOException e) {
			e.printStackTrace();
	    }
	}
	
	/**
	 * Get list of filtered tweets.
	 * 
	 * @return
	 */
	public ArrayList<String> getListOfFilteredTweet() {
		return listOfFilteredTweet;
	}
	
	public HashMap<Integer, String> getListOfTweetId() {
		return listOfTweetId;
	}
}
