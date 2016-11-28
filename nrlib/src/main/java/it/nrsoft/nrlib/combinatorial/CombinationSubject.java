package it.nrsoft.nrlib.combinatorial;

import it.nrsoft.nrlib.pattern.SubjectHelper;

public class CombinationSubject extends Combination {
	
	
	protected SubjectHelper subject = new SubjectHelper();
	

	/**
	 * @return the subject
	 */
	public SubjectHelper getSubject() {
		return subject;
	}

	public CombinationSubject(int maxSymbol, int wordLen) {
		super(maxSymbol, wordLen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generated(int[] generatedWord) {
		subject.notify(generatedWord);

	}

}
