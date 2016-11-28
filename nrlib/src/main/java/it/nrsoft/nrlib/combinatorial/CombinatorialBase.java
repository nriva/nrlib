package it.nrsoft.nrlib.combinatorial;

public abstract class CombinatorialBase
{
	/**
	Elenco di simboli da utilizzare
	*/
	protected int[] symbols;
	/**
	Lunghezza della parola da generare
	*/
	protected int wordLen;
	/**
	Parola corrente
	*/
	protected int[] word;
	/**
	Cardinalit� dell'insieme di parole
	*/
	protected long cardinality;

	/**
	Costruttore della classe.
	@param maxSymbol
	@param wordLen
	*/
	public CombinatorialBase(int maxSymbol,int wordLen)
	{
		this.wordLen = wordLen;
		symbols = new int[maxSymbol];
		for(int i=0;i<maxSymbol;i++)
			symbols[i] = i;
		word = new int[wordLen];
	}

	/**
	 * Confronta due parole
	 * @param word1 Prima parola
	 * @param word2 Seconda parola
	 * @return true se le due parole sono uguali, false altrimenti
	 */
	protected boolean areWordsEqual(int[] word1, int[] word2)
	{
		boolean areEqual = word1.length == word2.length;
		int i=0;
		if(areEqual)
		{
			while(i<word1.length && areEqual)
			{
				areEqual = word1[i] == word2[i];
				i++;
			}

		}
		return areEqual;
	}

	/**
	Metodo astratto da chiamare per ottenere la generazione delle
	varie combinazioni.
	Il metodo va sovrascritto per implementare la logica di generazione
	dei vari tipi di calcolo:<br/>
	1. Disposizioni<br/>
	2. Permutazioni<br/>
	3. Combinazioni<br/>
	4. Combinazioni con ripetizione
	*/
	public abstract void generate();

	/**
	Metodo astratto di "consumo" della parola (i.e. l'array di interi)
	prodotta. E' stato sviluppato questo meccanismo per disaccoppiare
	la produzione della parola dal suo consumo. Esistono principalmente modi di
	consumo delle parole prodotte:<br/>
	1. Utilizzarle direttamente in questo metodo<br/>
	2. Memorizzarla in una lista
	
	@param generatedWord
	*/
	public abstract void generated(int[] generatedWord);

	/**
	 * @return the cardinality
	 */
	public long getCardinality() {
		return cardinality;
	}


	/// <summary>
	/// Metodo di utilit� che trasforma un array di interi
	/// in una stringa leggibile.
	/// </summary>
	/// <param name="a">Array da trasformare</param>
	/// <returns>La stringa che rappresenta l'array</returns>
//	public static string IntArrayToString(int[] a)
//	{
//		string s="";
//		if(a.Length>0)
//			s = a[0].ToString();
//		for(int i=1;i<a.Length;i++)
//			s += "," + a[i].ToString();
//		return s;
//	}

}
