package preprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

public class Stop_Word {
	
	String path = "stop_word_list.txt";
	ArrayList<String> list = new ArrayList<String>(); 
	
	public Stop_Word() {
		initalizeStopWordList();
	}
	
	private void initalizeStopWordList() {
		try (BufferedReader br = new BufferedReader(new FileReader(path)))
		{
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				list.add(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Remove stop words from string.
	 */
	public String removeStopWordFromString(String string) {
		return string;
	}
}
