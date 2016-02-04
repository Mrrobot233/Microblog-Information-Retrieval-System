package test;

import java.util.HashMap;
import indexing.*;

public class Test_Indexer {
	public static void main(String[] args) {
		Custom_Indexing customIndexer = new Custom_Indexing();
		customIndexer.createInvertedIndex();
		
		HashMap<String, HashMap<Integer, Integer>> invertedIndex = customIndexer.getInvertedIndex();
		invertedIndex.forEach((k,v)->System.out.println(k));
		System.out.println("Total number of tokens in the file: " + customIndexer.getNumberOfTokens());
		System.out.println("Total number of tokens in the inverted index: " + invertedIndex.size());
	}
}
