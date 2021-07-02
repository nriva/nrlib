package it.nrsoft.nrlib.combinatorial;

/**
 Combinazione di n simboli a k a k.
*/
public abstract class Combination extends CombinatorialBase
{
	/**
	* Crea la classe per ottenere le combinazioni.
	* </summary>
	* <param name="maxSymbol">Valore massimo del simbolo (n-1)</param>
	* <param name="wordLen">Lunghezza della parola da generare (k)</param>
	*/ 
	public Combination(int maxSymbol,int wordLen) 
	{
		super(maxSymbol,wordLen);
		cardinality = CombinatorialCalc.BinomialFactor(symbols.length,wordLen);
	}

	/**
	* Crea un sottoinsieme dall'insieme dato di simboli.
	* @param symbols Insieme di simboli
	* @param excluded Primo elemento da escludere
	* @return Il sottoinsieme calcolato
	*/ 
	protected int[] subset(int[] symbols,int excluded)
	{
		
		int j=0,i;
		for(i=0;i<symbols.length;i++)
			if ( symbols[i] > excluded)
				break;
		int[] sset = new int[symbols.length-i];		
		for(;i<symbols.length;i++)
			sset[j++] = symbols[i];
		return sset;
	}

	/**
	* Genera le parole delle combinazioni.
	*/
	@Override
	public void generate()
	{
		int[] newWord = new int[wordLen];
		for(int i = 0;i < wordLen;i++)
			newWord[i] = 0;
		generate(newWord,0,symbols);	
	}

	/**
	* Genera le parole delle combinazioni.
	* @param newWord Parola in generazione
	* @param len Lunghezza della "sottoparola" gi� generata
	* @param symbols Simboli da utilizzare
	*/ 
	protected void generate(int[] newWord,int len,int[] symbols)
	{
		int[] workSave = newWord.clone();
		int[] work = new int[wordLen];
		int j;
		for(int i=0;i<len;i++)
			work[i] = workSave[i];

		if(len<wordLen) {
			for(j=0;j<symbols.length;j++)
			{
				work[len] = symbols[j];
				generate(work,len+1,subset(symbols,symbols[j]));
			}
		}
		else
		{
			generated(newWord);
		}
	}
}
