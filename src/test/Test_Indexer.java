package test;

import java.util.HashMap;
import java.util.Map.Entry;

import indexing.*;

public class Test_Indexer {
	
	/**
	 * This just creates an inverted index and prints out the contents to console for testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Custom_Indexing customIndexer = new Custom_Indexing();
		
		long startTime = System.nanoTime();
		customIndexer.createInvertedIndex();
//		printInvertedIndex(invertedIndex); only use this between creating the inverted index and calculating the weights
		customIndexer.calculateWeights();
		long endTime = System.nanoTime();
		System.out.println((endTime - startTime) / 1000000000.0);
		
//		printWeights(customIndexer.getInvertedIndex());
	}
	
	/**
	 * This method is just used to display the contents of the invertedIndex
	 * 
	 * @param invertedIndex
	 */
	private static void printInvertedIndex(HashMap<String, HashMap<Integer, Integer>> invertedIndex) {
		System.out.println("{");
		for(Entry<String, HashMap<Integer, Integer>> entry : invertedIndex.entrySet()) {
		    System.out.print("  " + entry.getKey() + ": {");
		    entry.getValue().forEach((k,v)->System.out.print(k + ": " + v + ", "));
		    System.out.print("}\n");
		}
		System.out.println("}");
	}
	
	/**
	 * This method is just used to display the contents of the weightMatrix
	 * 
	 * @param weightMatrix
	 */
	private static void printWeights(HashMap<String, HashMap<Integer, Double>> invertedIndex) {
		for(Entry<String, HashMap<Integer, Double>> tokens : invertedIndex.entrySet()) {
			for(Entry<Integer, Double> documentWeight : tokens.getValue().entrySet()) {
				System.out.println(tokens.getKey() + "x" + documentWeight.getKey() + " = " + documentWeight.getValue());
			}
		}
	}
}
