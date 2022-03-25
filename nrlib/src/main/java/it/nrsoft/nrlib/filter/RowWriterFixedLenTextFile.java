package it.nrsoft.nrlib.filter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RowWriterFixedLenTextFile extends RowWriterFixedLen {
	
	private String filename;
	
	/**
	 * @param filename
	 */
	public RowWriterFixedLenTextFile(String filename) {
		this.filename = filename;
	}

	@Override
	protected PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return new PrintWriter(new BufferedWriter(new FileWriter(filename)));
	}

}
