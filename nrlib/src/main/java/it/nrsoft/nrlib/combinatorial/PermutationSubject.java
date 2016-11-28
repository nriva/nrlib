package it.nrsoft.nrlib.combinatorial;

import it.nrsoft.nrlib.pattern.SubjectHelper;

public abstract class PermutationSubject extends Permutation {
	
	public PermutationSubject(int maxSymbol) {
		super(maxSymbol);
		// TODO Auto-generated constructor stub
	}


	protected SubjectHelper subject = new SubjectHelper();

	

	/**
	 * @return the subject
	 */
	public SubjectHelper getSubject() {
		return subject;
	}



	@Override
	public void generated(int[] generatedWord) {
		subject.notify(generatedWord);

	}

}
