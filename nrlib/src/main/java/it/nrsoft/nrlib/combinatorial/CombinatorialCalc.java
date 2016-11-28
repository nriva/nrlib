package it.nrsoft.nrlib.combinatorial;

public class CombinatorialCalc {
	
	/**
	 * Calcola il fattoriale n!
	 * @param n n
	 * @return il fattoriale di n
	 */
	public static long Fact(long n)
	{
		long f = 1;
		for(long i = 1; i<=n; i++)
			f = f * i;
		return f;
	}

	/**
	 * Calcola la potenza intera di un numero intero.
	 * @param n Base della potenza
	 * @param k Esponente della potenza
	 * @return La potenza n^k
	 */
	public static long Pow(long n, long k)
	{
		long p = 1;
		for(long i=0;i<k;i++)
			p *= n;
		return p;
	}

	/**
	 * Calcola il fattorie binomiale:<br/>
	 *	(n)<br/>
	 *  (m)
	 * @param n n
	 * @param m m
	 * @return Il fattore binomiale di n su m
	 */
	/// <summary>
	/// <br/>
	/// (n)<br/>
	/// (m)
	/// </summary>
	/// <param name="n">n</param>
	/// <param name="m">m</param>
	/// <returns>Il fattore binomiale di n su m.</returns>

	public static long BinomialFactor(int n, int m)
	{
		long[] b = new long[n+1];
		b[0] = 1;
		for(int i=1; i<=n; ++i)
		{
			b[i] = 1;
			for(int j=i-1; j>0; --j)
				b[j] += b[j-1];
		}
		return b[m];
	}


}
