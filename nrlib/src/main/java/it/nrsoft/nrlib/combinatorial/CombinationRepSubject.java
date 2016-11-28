package it.nrsoft.nrlib.combinatorial;

import it.nrsoft.nrlib.pattern.SubjectHelper;

public class CombinationRepSubject extends CombinationRep {

	public CombinationRepSubject(int maxSymbol, int wordLen) {
		super(maxSymbol, wordLen);
	}

	
	
    protected SubjectHelper subject = new SubjectHelper();
    
    


    /**
	 * @return the subject
	 */
	public SubjectHelper getSubject() {
		return subject;
	}




	@Override
    public void generated(int[] generatedWord)
	{
		subject.notify(generatedWord);
	}	

}
