package it.nrsoft.nrlib.html.test;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import it.nrsoft.nrlib.html.*;


public class TestHtmlFormatter {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
//		HtmlTableFormatter tf = new HtmlTableFormatter();
		SimpleHtmlFormatter shf = new SimpleHtmlFormatter();
		//tf.attrTable ="border='2'";
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\temp\\output.html"));
		bw.write(shf.table(
				new String[] {"head1","head2"},
				new String[][] { { shf.bold("row11"),"row12"},
							     { shf.italic("row21"),"row22"}}));
		bw.close();

	}

}
