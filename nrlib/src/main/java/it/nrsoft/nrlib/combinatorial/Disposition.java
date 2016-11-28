package it.nrsoft.nrlib.combinatorial;

/**
* Disposizioni di n simboli presi a k a k
*/

public abstract class Disposition extends CombinatorialBase
{
	private int[] firstWord;
	private int[] lastWord;

	public Disposition(int maxSymbol,int wordLen) 
	{
		super(maxSymbol,wordLen);
		this.firstWord = new int[wordLen];
		computeFirstWord();
		this.lastWord = new int[wordLen];
		computeLastWord();
		cardinality = CombinatorialCalc.Pow(symbols.length, wordLen);
		word = new int[0];
	}

	private void computeLastWord()
	{
		for(int i=0;i<wordLen;i++)
			lastWord[i] = symbols[symbols.length-1];
	}

	private void computeFirstWord()
	{

		for(int i=0;i<wordLen;i++)
			firstWord[i] = symbols[0];
	}

	protected boolean next() 
	{
		return next(0);
	}

	protected boolean next(int pos)
	{
		
		if(areWordsEqual(word,lastWord))
			return false;

		if(word.length == 0)
		{
			word = (int[])firstWord.clone();
			return true;
		}

		int symbolIndex = word[pos];

		if ( symbolIndex < symbols.length - 1)
			word[pos] = symbols[symbolIndex+1];
		else
		{
			word[pos] = symbols[0];
			if(pos < wordLen)
				next(pos+1);
		}
		return true;
	
	}

	@Override
	public void generate()
	{
		while(next(0))
			generated(word);
	}
}