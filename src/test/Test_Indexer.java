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
		customIndexer.createInvertedIndex();
		
		HashMap<String, HashMap<Integer, Integer>> invertedIndex = customIndexer.getInvertedIndex();
		
		System.out.println("{");
		for(Entry<String, HashMap<Integer, Integer>> entry : invertedIndex.entrySet()) {
		    System.out.print("  " + entry.getKey() + ": {");
		    entry.getValue().forEach((k,v)->System.out.print(k + ": " + v + ", "));
		    System.out.print("}\n");
		}
		System.out.println("}");
	}
}
