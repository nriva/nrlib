package it.nrsoft.nrlib.combinatorial;

public abstract class CombinationRep extends CombinatorialBase {

	public CombinationRep(int maxSymbol, int wordLen) {
		super(maxSymbol, wordLen);
		cardinality = CombinatorialCalc.Fact(symbols.length) / CombinatorialCalc.Fact(symbols.length - wordLen);
	}

	@Override
	public void generate() {
		int[] newWord = new int[wordLen];
		for(int i = 0;i<wordLen;i++)
			newWord[i] = 0;
		generate(newWord,0,symbols);	

	}

	
	protected void generate(int[] newWord,int len,int[] symbols)
	{
		int[] workSave = (int[])newWord.clone();
		int[] work = new int[wordLen];
		int j;
		for(int i=0;i<len;i++)
			work[i] = workSave[i];

		if(len<wordLen)
			for(j=0;j<symbols.length;j++)
			{
				work[len] = symbols[j];
				generate(work,len+1,subset(symbols,symbols[j]));
			}
		else
			generated(newWord);
	}
	

	protected int[] subset(int[] symbols,int excluded)
	{
		int[] sset = new int[symbols.length-1];
		int j=0;
		for(int c : symbols)
			if ( c != excluded)
				sset[j++] = c;
		return sset;
	}	

}
