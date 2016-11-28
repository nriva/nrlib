package it.nrsoft.nrlib;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import it.nrsoft.nrlib.util.ZipFile;



public class UnitTestZip {


	public final void test() throws IOException {
		
		
		String[] a  = new String[]{"acc1.accdb",
				"doc1.docx",
				"Folder1\\pp1.pptx"};
		
		List<String> x = Arrays.asList(a);
		
		ZipFile.zipFile("C:\\TEMP\\ZipExample",
						x,
					"c:\\temp\\prova.zip");
	}

}
