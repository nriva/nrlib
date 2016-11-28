package it.nrsoft.nrlib.filter;

import java.util.List;

public class CommaSepRow extends Row {

	public CommaSepRow(List<String> parts) {
		super(parts);
	}

	@Override
	public String getString() {

		String s = "";
		for(String part : parts)
		{
			if(!"".equals(s))
				s+=";";
			s += part;
		}
		return s;
	}

}
