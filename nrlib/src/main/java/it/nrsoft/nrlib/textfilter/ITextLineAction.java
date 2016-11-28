package it.nrsoft.nrlib.textfilter;

import java.util.List;

@Deprecated
public interface ITextLineAction {
	
	boolean perform(List<TextLine> lines);

}
