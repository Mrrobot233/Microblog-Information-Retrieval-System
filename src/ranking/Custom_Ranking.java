package ranking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import indexing.Custom_Indexing;
import preprocessing.Custom_Tokenizer;
import preprocessing.Stop_Word;

public class Custom_Ranking {
	
	private String query_list_path = "query.txt";
	private String evaluation_result_path = "evaluation_result.txt";
	private Custom_Indexing customIndexing;
	private ArrayList<Query> queryList = new ArrayList<Query>();
	private Custom_Tokenizer customTokenizer;
	private Stop_Word stopWord = new Stop_Word();
	
	public Custom_Ranking() {
		
		customIndexing = new Custom_Indexing();
		customIndexing.createInvertedIndex();
		customIndexing.calculateWeights();
		
		customTokenizer = new Custom_Tokenizer();
		customTokenizer.createFilteredListOfTweetFromFilteredDocument();
		customTokenizer.createTweetIdReference();
	}
	
	public void buildQueryList() {
		try (BufferedReader br = new BufferedReader(new FileReader(query_list_path))) 
		{
			String sCurrentLine;
			int id = 0;
			
			System.out.println("Creating list of queries.");
			Query tempQuery = new Query();
			try {
				while ((sCurrentLine = br.readLine()) != null) {
					if (sCurrentLine.split(" ")[0].equals("<num>")) 
					{
						tempQuery.setId(Integer.toString(++id));
					} 
					else if (sCurrentLine.split(" ")[0].equals("<title>")) 
					{
						sCurrentLine = sCurrentLine.replaceAll("<title> ", "");
						sCurrentLine = sCurrentLine.replaceAll("</title>", "");
						tempQuery.setTitle(sCurrentLine);
						queryList.add(tempQuery);
						tempQuery = new Query();
					}
				}
			} finally {
				br.close();
		    }
			
			System.out.println("Finished list of queries.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void evaluateListOfQuery() {
		
		try {
			PrintWriter pw = new PrintWriter(evaluation_result_path);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < queryList.size(); i++) {
			evaluateQuery(i);
		}

	}
	
	public void evaluateQuery(int queryNumber) {
		ArrayList<EvaluationResult> evaluationResultList = new ArrayList<EvaluationResult>();
		Set<Integer> listOfRelevantDocument;
		Query query = queryList.get(queryNumber);
		String title = query.getTitle().toLowerCase();
		title = removeNonAlphabetFromString(title);

		String[] wordList = title.split("\\s+");
		
		for (int i = 0; i < wordList.length; i++) {
			if (!stopWord.isStopWord(wordList[i])){
				title += wordList[i] + " ";
			}
		}
		title = title.trim();
		
		double cosineSimilarity;
		String[] listOfQueryWord = title.split(" ");
		HashMap<String, Double> queryWordWeightList = getQueryWordWeight(listOfQueryWord);
		listOfRelevantDocument = getListOfRelevantDocument(listOfQueryWord);

		for (int documentNumber : listOfRelevantDocument) {
			cosineSimilarity = getCosineSimilarity(
					listOfQueryWord,
					queryWordWeightList,
					documentNumber, 
					customTokenizer.getListOfFilteredTweet().get(documentNumber));
			
			if (cosineSimilarity > 0) {
				EvaluationResult evaluationResult = new EvaluationResult();
				evaluationResult.setCosineSimilarityValue(cosineSimilarity);
				evaluationResult.setDocNum(customTokenizer.getListOfTweetId().get(documentNumber + 1));
				evaluationResult.setTopicId(query.getId());
				evaluationResult.setTag("myRun");
				evaluationResultList.add(evaluationResult);
			}
		}

		Collections.sort(evaluationResultList);
		Collections.reverse(evaluationResultList);
		
		printEvaluationResultsToTextFile(evaluationResultList);
	}
	
	private void printEvaluationResultsToTextFile(ArrayList<EvaluationResult> evaluationResultList) {
		try {

			System.out.println("Printing Results for Query");
			System.out.println("Result size: " + evaluationResultList.size());
			
			BufferedWriter out = new BufferedWriter(new FileWriter(evaluation_result_path, true));
			DecimalFormat df = new DecimalFormat("0.000"); 
			
			int maxResult = 1000;
			if (maxResult > evaluationResultList.size()) {
				maxResult = evaluationResultList.size();
			}
			
			for (int i = 0; i < maxResult; i++) {
				EvaluationResult evaluationResult = evaluationResultList.get(i);
				
				out.write(evaluationResult.getTopicId() + " " +
						evaluationResult.getQ0() +  " " +
						evaluationResult.getDocNum() +  " " +
						(i + 1) +  " " +
						df.format(evaluationResult.getCosineSimilarityValue()) + " " +
						evaluationResult.getTag());
				
				out.newLine();
			}
			
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
	    }
	}
	
	private double getCosineSimilarity(String[] listOfQueryWord, HashMap<String, Double> queryWordWeightList, int documentNumber, String document) {
		
		HashMap<String, Double> documentMap = new HashMap<String, Double>();
		HashMap<String, Double> queryMap = new HashMap<String, Double>();
		
		String[] listOfDocumentWord = document.split(" ");
		
		for (String word : listOfDocumentWord) {
			documentMap.put(word, customIndexing.getInvertedIndex().get(word).get(documentNumber+1));
		}
		
		for (String word: listOfQueryWord) {
			if (queryMap.containsKey(word)) {
				queryMap.put(word, queryMap.get(word) + 1.0);
			} else {
				queryMap.put(word, 1.0);
			}
		}
		
		double maxQueryFrequency = 0;
		
		for (String word: queryMap.keySet()) {
			if (queryMap.get(word) > maxQueryFrequency) {
				maxQueryFrequency = queryMap.get(word);
			}
		}

		for (String word: queryMap.keySet()) {
			queryMap.put(word, (queryMap.get(word)/maxQueryFrequency)*queryWordWeightList.get(word));
		}
		
		double dotProductValue = 0.0;
		double documentSquaredSum = 0.0;
		double querySquaredSum = 0.0;
		
		for (String word : listOfDocumentWord) {
			double wordCount = (double) documentMap.get(word);
			documentSquaredSum += (wordCount * wordCount);
			
			if (queryMap.containsKey(word)) {
				dotProductValue += (wordCount * queryMap.get(word));
			}
		}
		
		for (String word: queryMap.keySet()) {
			double wordWeight = (double) queryMap.get(word);
			querySquaredSum += wordWeight * wordWeight;
		}
		
		return dotProductValue / (Math.sqrt(documentSquaredSum * querySquaredSum));
	}
	
	private Set<Integer> getListOfRelevantDocument(String[] listOfQueryWord) {
		Set<Integer> listOfRelevantDocument = new HashSet<Integer>();
		for (int i = 0; i < listOfQueryWord.length; i++) {
			if (customIndexing.getInvertedIndex().containsKey(listOfQueryWord[i])) {
				listOfRelevantDocument.addAll(customIndexing.getInvertedIndex().get(listOfQueryWord[i]).keySet());
			}
		}
		return listOfRelevantDocument;
	}
	
	private HashMap<String, Double> getQueryWordWeight(String[] listOfQueryWord) {
		HashMap<String, Double> queryWordWeight = new HashMap<String, Double>();
		for (int i = 0; i < listOfQueryWord.length; i++) {
			if (customIndexing.getInvertedIndex().containsKey(listOfQueryWord[i])) {
				
				double totalOccurrence = 0;
				for (double val : customIndexing.getInvertedIndex().get(listOfQueryWord[i]).values()) {
					totalOccurrence += val;
				}
				
				queryWordWeight.put(listOfQueryWord[i], (double) (Math.log(45899/totalOccurrence)));
			} else {
				queryWordWeight.put(listOfQueryWord[i], 0.0);
			}
		}
		return queryWordWeight;
	}
	
	/**
	 * Remove non alphabets from string.
	 * 
	 * @param string
	 * @return
	 */
	private String removeNonAlphabetFromString(String string) {
		string = string.replaceAll("-", " ");
		string = string.replaceAll("[^A-Za-z ]", ""); //Kept twitter hashtags... might be useful
		return string;
	}
}
