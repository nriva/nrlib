package it.nrsoft.nrlib.combinatorial;

public class CombinationRepSubjectList extends CombinationRepSubject {
	
	private WordList wordList = new WordList();
	

	/**
	 * @return the wordList
	 */
	public WordList getWordList() {
		return wordList;
	}


	public CombinationRepSubjectList(int maxSymbol, int wordLen) {
		super(maxSymbol, wordLen);
		subject.addObserver(wordList);
	}

}
