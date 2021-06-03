package it.nrsoft.nrlib.math;

import java.util.*;

import it.nrsoft.nrlib.combinatorial.CombinatorialCalc;

public class RomanNotation
{
    private static Map<String, Integer> values = new HashMap<String, Integer>();
	private static List<String> ordval = new ArrayList<String>();

	//values = { "I" : 1, "V" : 5, "X" : 10, "L" : 50, "C" : 100, "D" : 500, "M" : 1000 }	
	//ordval = ["I","V","X","L","C","D","M"]

	// "decine"
	//tens = ["I","X","C"]
	private static char[] tens = new char[] {'I','X','C'};

	// "cinquine"
	//fives = ["V","L","D"]
	private static char[] fives = new char[] {'V','L','D'};



	static
	{
		values.put("I",1);
		values.put("V",5);
		values.put("X",10);
		values.put("L",50);
		values.put("C",100);
		values.put("D",500);
		values.put("M",1000);
		
		ordval.addAll( Arrays.asList(new String[] {"I","V","X","L","C","D","M"}));
//		Collections.reverse(ordval);
	}


	public static int romanToInt(String roman)
	{
		int s = 0;
		int i = 0;

		while(i< roman.length()-1)
		{
			if( (int)values.get(roman.substring(i,1)) < (int)values.get(roman.substring(i+1,1)) )
				s -= values.get(roman.substring(i,1));
			else
				s += values.get(roman.substring(i,1));
			i++;
		}
		s += values.get(roman.substring(i,1));
		return s;
	
	}

	private static String normalize(String roman)
	{
		String r = roman;

		// cerca corse di cifre uguali

		boolean found = true;
		while(found)
		{
			found = false;
			for(int t=0;t<tens.length;t++)
			{
				int i = 0;
				while( i < r.length())
				{
					if (r.charAt(i) == tens[t])
					{
						int start = i;
						int stop = i;
						while (stop < r.length() && r.charAt(stop) == tens[t])
							stop = stop + 1;
						found = ((stop - start) == 4);
						if (found)
						{
							r = r.substring(0,start) + tens[t] + fives[t] + r.substring(stop);
							i = start;
						}
					}	
					i = i + 1;
				}
			}
		}
			
		r = r.replace("VIV","IX").replace("LXL","XC").replace("DCD","CM");
		return r;
	}
	
	public static String intToRoman(int num)
	{
		/*Saving Roman equivalent of the thousand, hundred, ten and units place of a decimal number*/
		String thou[]={"","M","MM","MMM"};
		String hund[]={"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
		String ten[]={"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
		String unit[]={"","I","II","III","IV","V","VI","VII","VIII","IX"};
		/*Finding the digits in the thousand, hundred, ten and units place*/
		int th=num/1000;
		int h=(num/100)%10;
		int t=(num/10)%10;
		int u=num%10;		
		
		return thou[th]+hund[h]+ten[t]+unit[u];
	}

/*    public static String intToRoman(int integer)
	{
		// Trasforma un numero in notazione romana	
		int value = 0;
		int reminder = integer;
		String s = "";
		String sym = "";
		
		while (reminder!=0)
		{
			value = 0;
			
	    	String svalue = String.valueOf(integer);
	    	int upperLimit=0;
	    	if(svalue.charAt(0)<'9')
	    	{
	    		upperLimit = (int)(svalue.charAt(0)-'0'+1) * (int)CombinatorialCalc.Pow(10, svalue.length()-1); 
	    	}
	    	else
	    		upperLimit = (int)CombinatorialCalc.Pow(10, svalue.length());
	    	
	    	int diff = upperLimit-integer;
	    	
	    	if(diff==1)
	    		s+="I";
			
			switch(reminder)
			{
				case 9:
					s += "IX";
					reminder=0;
					break;
			
//				case 99:
//					s += "IC";
//					reminder=0;
//					break;
//				case 999:
//					s+="IM";
//					reminder=0;
//					break;
//				case 990:
//					s+="XM";
//					reminder=0;
//					break;					
			}			
			
			
			if(reminder!=0) {
				for(String v : ordval)
					if ( values.get(v) <= reminder )
					{
						value = values.get(v);
						sym = v;
					}
			
				s += sym;
				reminder -= value;
			}
		}
		return s;
	}
	*/
}
