package test;

import java.io.FileNotFoundException;

import ranking.Custom_Ranking;

public class Test_Ranker {
	
	public static void main(String args[]) {
		Custom_Ranking customRanking = new Custom_Ranking();
		customRanking.buildQueryList();
		customRanking.evaluateListOfQuery();
	}
	
}
