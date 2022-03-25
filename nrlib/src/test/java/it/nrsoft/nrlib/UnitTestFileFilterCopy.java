package it.nrsoft.nrlib;


import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;

import it.nrsoft.nrlib.filter.FileFilter;
import it.nrsoft.nrlib.filter.FileFilterActionSet;
import it.nrsoft.nrlib.filter.RowReader;
import it.nrsoft.nrlib.filter.RowReaderFixedLenTextFile;
import it.nrsoft.nrlib.filter.RowWriter;
import it.nrsoft.nrlib.filter.RowWriterFixedLenTextFile;




public class UnitTestFileFilterCopy extends UnitTestFileFilter {
	
	
	private String[] testcase_check = {
			 "1234567890123451234567890"
			,"2234567890123451234567890"
			,"3234567890123451234567890"
			,"4234567890123451234567890"
			};
	

	@Test
	public final void test() throws IOException {
		RowReader reader = new RowReaderFixedLenTextFile(new int[] {10,5,10}, "filter.in");
		RowWriter writer = new RowWriterFixedLenTextFile("filter.out");
		
		FileFilter filter = new FileFilter(reader, writer);
		filter.setAction(FileFilterActionSet.parse("copy(from:3,to:2)"));
		
		filter.perform();
		
		
		BufferedReader r = new BufferedReader(new FileReader("filter.out"));
		String buffer;
		
		int tc=0;
		
		while((buffer = r.readLine())!=null)
		{
			assertEquals("riga " + tc, testcase_check[tc], buffer);
			tc++;
		}
		
	}

}
