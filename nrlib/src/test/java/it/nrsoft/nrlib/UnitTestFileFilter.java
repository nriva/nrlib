package it.nrsoft.nrlib;

import java.io.*;

import org.junit.Before;


public class UnitTestFileFilter {

	protected String[] testcase = {
				 "1234567890ABCDE1234567890"
				,"2234567890ABCDE1234567890"
				,"3234567890ABCDE1234567890"
				,"4234567890ABCDE1234567890"
				};

	@Before
	public void setUp() throws Exception {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("filter.in"));
		for(String s : testcase)
			writer.write(s+"\r\n");
		
		writer.close();
		
	}



}