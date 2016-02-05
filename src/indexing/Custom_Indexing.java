package indexing;

import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class Custom_Indexing {
	private String filtered_tweet_list_path = "filtered_tweet_list.txt";
	private HashMap<String, HashMap<Integer, Integer>> invertedIndex = new HashMap<String, HashMap<Integer, Integer>>();
	private HashMap<Integer, Integer> documentOccurrences;
	private double[][] weightedMatrix;
	private int totalNumberOfDocuments;
	
	public Custom_Indexing() {
		invertedIndex = new HashMap<String, HashMap<Integer, Integer>>();
		totalNumberOfDocuments = 0;
		weightedMatrix = new double[0][0];
	}
	
	/**
	 * Loop through the filtered Tweets list and create an inverted index 
	 */
	public void createInvertedIndex() {
		String[] documentTokens;
		
		try (Scanner sc = new Scanner(new FileReader(filtered_tweet_list_path))) {
			while(sc.hasNextLine()) {
				documentTokens = sc.nextLine().split(" ");
				totalNumberOfDocuments++;
				
				for (int i=0; i<documentTokens.length; i++) {
					addTokenToInvertedIndex(documentTokens[i], totalNumberOfDocuments);
				}
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
	 * Loop through the invertedIndex and create a matrix that 
	 */
	public void createWeightIndex() {
		int tokenNumber = 0;
		int maxF = findMaxFrequency();
		weightedMatrix = new double[getTotalNumerOfDocuments()][invertedIndex.size()];
		
		for(Entry<String, HashMap<Integer, Integer>> tokens : invertedIndex.entrySet()) {
			tokenNumber++;
			for(Entry<Integer, Integer> documentFreq : tokens.getValue().entrySet()) {
				if ((documentFreq.getKey()-1) < weightedMatrix.length) {
					weightedMatrix[documentFreq.getKey()-1][tokenNumber] = calculateWeight(documentFreq.getValue(), maxF, tokens.getValue().size());
				}
			}
		}
	}
	
	/**
	 * Calculate the term weight
	 * 
	 * @param freq: frequency of item in document
	 * @param maxF: max frequency of any item in any document
	 * @param dI: number of documents containing term i
	 * @return
	 */
	private double calculateWeight(int freq, int maxF, int dI) {
		return (freq / maxF) * getLogBase2(getTotalNumerOfDocuments()/dI);
	}
	
	
	/**
	 * Get log base 2 of an item
	 * 
	 * @param x
	 * @return
	 */
	private double getLogBase2(double x) {
		return Math.log(x)/Math.log(2);
	}
	
	/**
	 * Find the max frequency of any token in any doc
	 * 
	 * @return
	 */
	public int findMaxFrequency() {
		int max = 0;
		
		for(Entry<String, HashMap<Integer, Integer>> entry : invertedIndex.entrySet()) {
			for(Entry<Integer, Integer> entry2 : entry.getValue().entrySet()) {
				if (max < entry2.getValue()) {
					max = entry2.getValue();
				}
			}
		}
		
		return max;
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
	 * Get the weighted matrix
	 * 
	 * @return
	 */
	public double[][] getWeightedMatrix() {
		return weightedMatrix;
	}
	
	/**
	 * Get the weight of documentNum and tokenNum
	 * 
	 * @param documentNum
	 * @param tokenNum
	 * @return
	 */
	public double getWeightedValue(int documentNum, int tokenNum) {
		return weightedMatrix[documentNum][tokenNum];
	}
	
	/**
	 * Get the number of occurrences for a token in a specific document
	 * 
	 * @param number
	 * @return
	 */
	public int getNumberOfOccurencesofATokenInDocument(String token, int document) {
		return this.invertedIndex.get(token).get(document);
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
	 * Get the total number of key value pairs in the inverted index
	 * 
	 * @return
	 */
	public int getNumberOfKeyValuePairs() {
		return this.invertedIndex.size();
	}
	
	/**
	 * Get the total number of documents in the inverted index
	 * 
	 * @return
	 */
	public int getTotalNumerOfDocuments() {
		return this.totalNumberOfDocuments;
	}
}
