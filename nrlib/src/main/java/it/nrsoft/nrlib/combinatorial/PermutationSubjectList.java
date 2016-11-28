package it.nrsoft.nrlib.combinatorial;

public class PermutationSubjectList extends PermutationSubject {
	
	private WordList wordList = new WordList();

	public PermutationSubjectList(int maxSymbol) {
		super(maxSymbol);
		subject.addObserver(wordList);
	}

	/**
	 * @return the wordList
	 */
	public WordList getWordList() {
		return wordList;
	}

}
