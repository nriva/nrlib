package it.nrsoft.nrlib.filter.test;
import java.io.IOException;

import it.nrsoft.nrlib.filter.*;

public class TestFileFilter {
	
	
	public static void main(String[] args) throws IOException
	{
		
		RowReader reader = new RowReaderFixedLenTextFile(new int[] {10,5,10}, "c:/temp/filter.in");
		RowWriter writer = new RowWriterFixedLenTextFile("c:/temp/filter.out");
		
		FileFilter filter = new FileFilter(reader, writer);
		filter.setAction(FileFilterActionDecorator.parse("script(script:prova)")
				.setDecoratedAction(FileFilterActionDecorator.parse("blank(1)")));
				
		
		filter.perform();
		
	}
	
	

}
