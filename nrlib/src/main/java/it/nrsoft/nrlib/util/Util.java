package it.nrsoft.nrlib.util;


public class Util {
	
	
	private static <T extends Comparable<T>> T compare(int compareTo,T ... a )
	{
		
		T m = a[0];
		for(int i=0;i<a.length;i++)
			if(a[i].compareTo(m)>0 && compareTo>0 ||
					a[i].compareTo(m)<0 && compareTo<0)
				m=a[i];
		return m;
		
	}		
	
	public static<T extends Comparable<T>> T max(T ... a )
	{
		
		return compare(1,a);
		
	}
	
	
	public static<T extends Comparable<T>> T min(T ... a )
	{
		
		return compare(-1,a);
		
	}
	
	
	public static <T> T decode(T value,T ... alternatives )
	{

		T m = null;
		
		if(alternatives.length==0)
			return null;
		
		if(alternatives.length%2==1)
			m=alternatives[alternatives.length-1];
		
		for(int i=0;i<alternatives.length/2;i++)
			if(value!=null && value.equals(alternatives[2*i]))
			{
				m = alternatives[2*i+1];
				break;
			}
		return m;

		
	}
	
	public static <T> T coalesce(T ... values )
	{

		T m = null;
		
		for(int i=0;i<values.length;i++)
			if(values[i]!=null)
			{
				m = values[i];
				break;
			}
		return m;

		
	}	
	


}
