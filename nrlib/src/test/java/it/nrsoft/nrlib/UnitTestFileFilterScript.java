package it.nrsoft.nrlib;



import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.nrsoft.nrlib.filter.*;

public class UnitTestFileFilterScript extends UnitTestFileFilter {
	
	/**
	 * @see UnitTestFileFilter#setUp()
	 */
	//@Before
	public void setUp() throws Exception {
		super.setUp();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("provafilt.groovy"));
		writer.write("_2 = \"X\" + _2; return true");
		writer.close();		
	}

	protected String[] testcase_check = {
			 "1234567890XABCD1234567890"
			,"2234567890XABCD1234567890"
			,"3234567890XABCD1234567890"
			,"4234567890XABCD1234567890"
			};	
	
	@Test
	public final void test() throws IOException {
		RowReader reader = new RowReaderFixedLenTextFile(new int[] {10,5,10}, "filter.in");
		RowWriter writer = new RowWriterFixedLenTextFile("filter.out");
		
		FileFilter filter = new FileFilter(reader, writer);
		filter.setAction(FileFilterActionSet.parse("script(provafilt)"));
		
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
