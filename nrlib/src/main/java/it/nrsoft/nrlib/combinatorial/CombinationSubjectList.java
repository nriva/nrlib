package it.nrsoft.nrlib.combinatorial;

public class CombinationSubjectList extends CombinationSubject {

	public CombinationSubjectList(int maxSymbol, int wordLen) {
		super(maxSymbol, wordLen);
		subject.addObserver(wordList);
	}
	
	private WordList wordList = new WordList();

	/**
	 * @return the wordList
	 */
	public WordList getWordList() {
		return wordList;
	}
	
	
	

}
