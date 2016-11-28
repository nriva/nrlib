package it.nrsoft.nrlib.combinatorial;

import it.nrsoft.nrlib.pattern.SubjectHelper;

public abstract class DispositionSubject extends Disposition {
	
	protected SubjectHelper subject = new SubjectHelper();

	public SubjectHelper getSubject() { 
		return subject; 
	}
	

	public DispositionSubject(int maxSymbol, int wordLen) {
		super(maxSymbol, wordLen);
	}

	@Override
	public void generated(int[] generatedWord) {
		subject.notify(generatedWord);

	}

}
