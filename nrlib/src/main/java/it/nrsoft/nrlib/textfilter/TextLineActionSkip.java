package it.nrsoft.nrlib.textfilter;

import java.util.List;

@Deprecated
public class TextLineActionSkip implements ITextLineAction {
	
	int offset=0;
	int numOfLines = 1;
	String pattern = "";
	
	public TextLineActionSkip(String pattern, String[] params)
	{
		
		if(params.length>=1)
			offset = Integer.valueOf(params[0]);
		if(params.length>=2)
			numOfLines = Integer.valueOf(params[1]);
		this.pattern = pattern; 
	}


	public boolean perform(List<TextLine> lines) {
		
		
		for(int i=0;i<lines.size();i++)
			if("".equals(pattern) || lines.get(i).getLine().contains(pattern))
			{
				for(int n=0;n<numOfLines;n++)
					if(i+n+offset >= 0 && i+n+offset<lines.size())
						lines.get(i+n+offset).setOutput(false);
			}



		return true;
	}

}
