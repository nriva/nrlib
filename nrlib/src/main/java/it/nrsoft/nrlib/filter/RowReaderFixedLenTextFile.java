package it.nrsoft.nrlib.filter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class RowReaderFixedLenTextFile extends RowReaderFixedLen {
	
	private String filename;
	
	/**
	 * @param format
	 */
	public RowReaderFixedLenTextFile(int[] lengths,String filename) {
		super(lengths);
		this.filename = filename;
	}

	@Override
	protected BufferedReader getReader() throws FileNotFoundException {
		return new BufferedReader(new FileReader(filename));
	}

}
