package it.nrsoft.nrlib;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import it.nrsoft.nrlib.filter.*;

public class UnitTestFileFilterBlank extends UnitTestFileFilter {
	
	
	
	private String[] testcase_check = {
			 "1234567890     1234567890"
			,"2234567890     1234567890"
			,"3234567890     1234567890"
			,"4234567890     1234567890"};
	


	@Test
	public final void test() throws IOException {
		RowReader reader = new RowReaderFixedLenTextFile(new int[] {10,5,10}, "filter.in");
		RowWriter writer = new RowWriterFixedLenTextFile("filter.out");
		
		FileFilter filter = new FileFilter(reader, writer);
		filter.setAction(FileFilterActionSet.parse("blank(field:2)"));
		
		filter.perform();
		
		
		BufferedReader r = new BufferedReader(new FileReader("filter.out"));
		String buffer;
		
		int tc=0;
		
		while((buffer = r.readLine())!=null)
		{
			assertEquals("riga " + tc, testcase_check[tc], buffer);
			tc++;
		}
		r.close();
		
	}

}
