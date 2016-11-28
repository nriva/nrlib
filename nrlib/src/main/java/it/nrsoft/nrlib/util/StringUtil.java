package it.nrsoft.nrlib.util;

import java.util.*;

public class StringUtil {
	
	public static java.lang.String join(java.lang.String[] strings, java.lang.String separator)
	{
		StringBuilder sb = new StringBuilder();
		
		if(strings.length>=1)
			sb.append(strings[0]);
		if(strings.length>1)
			for(int i=1;i<strings.length;i++)
			{
				sb.append(separator);
				sb.append(strings[i]);
			}
		return sb.toString();
	}
	
	public static java.lang.String join(List<String> strings, java.lang.String separator)
	{
		StringBuilder sb = new StringBuilder();
		
		if(strings.size()>=1)
			sb.append(strings.get(0));
		if(strings.size()>1)
			for(int i=1;i<strings.size();i++)
			{
				sb.append(separator);
				sb.append(strings.get(i));
			}
		return sb.toString();
	}	
	
	
	/**
	 * Splitta una stringa in un dizionario.
	 * La stringa pu� essere in due formati:
	 * 		1) chiave1 = valore1 , chiave2 = valore2
	 * 		2) valore1 , valore2
	 * Nel secondo caso deve essere valorizzato il parametro keys e devono essere indicate tutte le 
	 * chiavi possibili. In caso di valore mancante deve essere indicato un valore vuoto (es. valore1, , valore3)
	 * @param string Stringa origine
	 * @param entrySep separatore delle voci del dizionario
	 * @param pairSep separatore di chiave e valore
	 * @param keys elenco delle chiavi da usare
	 * @return
	 */
	public static Map<String,String> dictionarySplit(String string,String entrySep, String pairSep,String[] keys)
	{
		HashMap<String,String> map = null;
		
		if(!"".equals(string)) {
			map = new HashMap<String,String>();
			String[] parts = string.split(entrySep);
			
			if(!pairSep.equals("") && parts[0].indexOf(pairSep)>=0)
				for(String part : parts)
				{
					String _part = part.trim();
					if(!_part.equals("")) {
						String[] pair = _part.split(pairSep);
						if(keys==null || Arrays.binarySearch(keys, pair[0].trim())>=0)
							map.put(pair[0].trim(),pair[1].trim());
					}
				}
			else
			{
				for(int i=0;i<keys.length;i++)
					if(!parts[i].trim().equals(""))
						map.put(keys[i].trim(),parts[i].trim());
			}
		}
		
		return map;
	}
	
	public static String newString(char c,int l)
	{
		char[] a = new char[l];
		
		for(int i=0;i<l;i++)
			a[i] = c;
		
		return new String(a);
	}
	
	public static String newString(String s,int l)
	{
		StringBuilder builder=new StringBuilder(l);
		for(int i=0;i<l;i++)
			builder.append(s);
		
		return builder.toString();
	}	

}
