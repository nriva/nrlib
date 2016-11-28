package it.nrsoft.nrlib.combinatorial;

import java.util.LinkedList;
import java.util.List;

public class Util {
	
	public static String wordAsString(int[] word, char[] symbols)
	{
		StringBuilder s=new StringBuilder(word.length);
		for(int l : word)
			s.append(symbols[l]);
		return s.toString();
	}
	
	public static List<String> wordListAsString(WordList wordList, char[] symbols)
	{
		List<String> words = new LinkedList<String>();
		for(int[] word : wordList)
		{
			words.add(wordAsString(word,symbols));
		}
		return words;
	}

}
