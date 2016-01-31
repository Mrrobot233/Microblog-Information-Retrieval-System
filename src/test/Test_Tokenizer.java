package test;

import preprocessing.*;

public class Test_Tokenizer {
	public static void main(String args[]) {
		Custom_Tokenizer customTokenizer = new Custom_Tokenizer();
		customTokenizer.createFilteredListOfTweetFromNonFilteredDocument();
		customTokenizer.addFilteredTweetToFilteredDocument();
	}
}
