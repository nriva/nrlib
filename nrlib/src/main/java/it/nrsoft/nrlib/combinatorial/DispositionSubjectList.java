package it.nrsoft.nrlib.combinatorial;

public class DispositionSubjectList extends DispositionSubject {
	
	private WordList wordList = new WordList();


	/**
	 * @return the wordList
	 */
	public WordList getWordList() {
		return wordList;
	}


	public DispositionSubjectList(int maxSymbol, int wordLen) {
		super(maxSymbol, wordLen);
		subject.addObserver(wordList);
	}

}
