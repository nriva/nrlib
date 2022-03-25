package it.nrsoft.nrlib.filter;

import java.util.*;
import it.nrsoft.nrlib.util.StringUtil;

public class FixedLenRow extends Row {

	private int[] lengths;
	public FixedLenRow(int[] lengths, List<String> parts) {
		super(parts);
		this.lengths = lengths;
	}

	@Override
	public String getString() {
		StringBuilder r = new StringBuilder();
		
		for(int i=0;i<lengths.length;i++)
		{
			
			String str = parts.get(i);
			
			if(str.length()<lengths[i])
				str += StringUtil.newString(' ', lengths[i] - str.length() );
			else if(str.length()>lengths[i])
				str = str.substring(0, lengths[i]);
			r.append(str);
		}
		
		
		return r.toString();
	}



}
