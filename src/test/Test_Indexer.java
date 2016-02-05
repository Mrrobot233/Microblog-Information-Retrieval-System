package test;

import java.util.ArrayList;
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
		customIndexer.createInvertedIndex();
		customIndexer.createWeightIndex();
		
//		HashMap<String, HashMap<Integer, Integer>> invertedIndex = customIndexer.getInvertedIndex();
//		printInvertedIndex(invertedIndex);
		printWeightMatrix(customIndexer.getWeightedMatrix());
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
	private static void printWeightMatrix(double[][] weightMatrix) {
		for (int doc=0; doc<weightMatrix.length; doc++) {
			for(int token=0; token<weightMatrix[doc].length; token++) {
				System.out.println("Weight: " + (doc+1) + "x" + (token+1) + " = " + weightMatrix[doc][token]);
			}
		}
	}
}
