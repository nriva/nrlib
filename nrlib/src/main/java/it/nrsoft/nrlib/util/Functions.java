package it.nrsoft.nrlib.util;

import java.util.Comparator;

public class Functions {
	
	
	public static class IntegerComparator implements Comparator<Integer>
	{

		@Override
		public int compare(Integer o1, Integer o2) {

			return o1.compareTo(o2);
		}
	}
	
	public static class DoubleComparator implements Comparator<Double>
	{

		@Override
		public int compare(Double o1, Double o2) {

			return o1.compareTo(o2);
		}
	}
	
	public static class FloatComparator implements Comparator<Float>
	{

		@Override
		public int compare(Float o1, Float o2) {

			return o1.compareTo(o2);
		}
	}	
	
	
	public static class LongComparator implements Comparator<Long>
	{

		@Override
		public int compare(Long o1, Long o2) {

			return o1.compareTo(o2);
		}
	}	
	
	public static IntegerComparator integerComparator = new IntegerComparator();

	
	
	/**
	 * Trova il massimo in un insieme di valori
	 * @param comp
	 * @param a
	 * @return il massimo trovato
	 * @throws Exception
	 */
    public static <T> T Max(Comparator<T> comp,T ... a) throws Exception 
    {
 
        if (a.length == 0)
            throw new Exception("Provide at least one value");

        if (a.length == 1)
            return a[0];
        T max;
        if (comp.compare(a[0],a[1]) >= 0)
            max = a[0];
        else
            max = a[1];
        for (int i = 2; i < a.length; i++)
            if (comp.compare(a[i],max) > 0)
                max = a[i];
        return max;
    }

    /**
     * Trova il minimo in un insieme di valori
     * @param comp
     * @param a Insieme di valori
     * @return il minimo trovato
     * @throws Exception
     */
    public static <T> T Min(Comparator<T> comp, T ... a) throws Exception
    {
        if (a.length == 0)
            throw new Exception("Provide at least one value");

        if (a.length == 1)
            return a[0];
        T min;
        if ( comp.compare(a[0], a[1]) <= 0)
            min = a[0];
        else
            min = a[1];
        for (int i = 2; i < a.length; i++)
            if (comp.compare(a[i],min) < 0)
                min = a[i];
        return min;
    }

    /// <summary>
    /// 
    /// </summary>
    /// <typeparam name="T"></typeparam>
    /// <param name="values"></param>
    /// <returns></returns>
    public static <T> T Coalesce(T ... values)
    {
        if (values != null)
            for (int i = 0; i < values.length; i++)
                if (values[i] != null)
                    return values[i];
        return null;
    }	

}
