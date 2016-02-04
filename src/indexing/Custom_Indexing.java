package indexing;

import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class Custom_Indexing {
	private String filtered_tweet_list_path = "filtered_tweet_list.txt";
	private HashMap<String, HashMap<Integer, Integer>> invertedIndex = new HashMap<String, HashMap<Integer, Integer>>();
	private HashMap<Integer, Integer> documentOccurrences;
	private int numberOfTokens;
	
	public Custom_Indexing() {
		invertedIndex = new HashMap<String, HashMap<Integer, Integer>>();
		numberOfTokens = 0;
	}
	
	/**
	 * Loop through the filtered Tweets list and create an inverted index 
	 */
	public void createInvertedIndex() {
		int documentNumber = 1; //each line is a document
		String[] documentTokens;
		
		try (Scanner sc = new Scanner(new FileReader(filtered_tweet_list_path))) {
			while(sc.hasNextLine()) {
				documentTokens = sc.nextLine().split(" ");
				
				for (int i=0; i<documentTokens.length; i++) {
					addTokenToInvertedIndex(documentTokens[i], documentNumber);
					numberOfTokens++;
				}
				documentNumber++;
			}
			sc.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Add token to the inverted index
	 * 
	 * @param documentToken
	 * @param documentNumber
	 */
	private void addTokenToInvertedIndex(String documentToken, int documentNumber) {
		if ((documentOccurrences = invertedIndex.get(documentToken)) != null) {
			
			if (documentOccurrences.containsKey(documentNumber)) {
				documentOccurrences.put(documentNumber, documentOccurrences.get(documentNumber)+1);
			} else {
				documentOccurrences.put(documentNumber, 1);
			}
			
			invertedIndex.put(documentToken, documentOccurrences);
		} else {
			documentOccurrences = new HashMap<Integer, Integer>();
			documentOccurrences.put(documentNumber, 1);
			invertedIndex.put(documentToken, documentOccurrences);
		}
	}
	
	/**
	 * Get the inverted index
	 * 
	 * @return
	 */
	public HashMap<String, HashMap<Integer, Integer>> getInvertedIndex() {
		return this.invertedIndex;
	}
	
	/**
	 * Get the number of occurrences for a 
	 * 
	 * @param number
	 * @return
	 */
	public int getNumberOfOccurencesofATokenInDocument(String token, int number) {
		return this.invertedIndex.get(token).get(number);
	}
	
	/**
	 * Get the documents where a token is present
	 * 
	 * @param token
	 * @return
	 */
	public HashMap<Integer, Integer> getDocumentsForToken(String token) {
		return this.invertedIndex.get(token);
	}
	
	/**
	 * Get total number of tokens read from file
	 * 
	 * @return
	 */
	public int getNumberOfTokens() {
		return this.numberOfTokens;
	}
	
	/**
	 * Get the total number of key value pairs in the inverted index
	 * 
	 * @return
	 */
	public int getNumberOfKeyValuePairs() {
		return this.invertedIndex.size();
	}
}
