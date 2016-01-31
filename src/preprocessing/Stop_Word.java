package preprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

public class Stop_Word {
	
	String path = "stop_word_list.txt";
	ArrayList<String> stopWordList = new ArrayList<String>(); 
	
	public Stop_Word() {
		initalizeStopWordList();
	}
	
	private void initalizeStopWordList() {
		try (BufferedReader br = new BufferedReader(new FileReader(path)))
		{
			String sCurrentLine;

			try {
				while ((sCurrentLine = br.readLine()) != null) {
					stopWordList.add(sCurrentLine);
				}
			} finally {
				br.close();
		    }
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public ArrayList<String> getStopWordList() {
		return stopWordList;
	}
	
	public boolean isStopWord(String string) {
		for (int i = 0; i < stopWordList.size(); i++) {
			if (stopWordList.get(i).equals(string.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
