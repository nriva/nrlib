package it.nrsoft.nrlib.filter;

import java.io.*;
import java.util.*;

public abstract class RowReaderFixedLen extends FixedLenData implements RowReader {
	
	private BufferedReader reader = null;
	private String buffer=null;
	
	
	
	public void open(FileFilterActionContext context) throws IOException {

		reader = getReader();
		
		buffer = reader.readLine();
		
	}

	protected abstract BufferedReader getReader() throws FileNotFoundException;

	public void close(FileFilterActionContext context) throws IOException {
		reader.close();
		
	}

	public boolean hasMoreRow() {
		return buffer!=null && !"".equals(buffer);
	}

	public Row read() throws IOException {
		

		List<String> parts = new LinkedList<String>();
		int i=0;
		int base=0;
		while(i<lengths.length && base < buffer.length())
		{
			parts.add( buffer.substring(base,base+lengths[i]));
			base += lengths[i];
			i++;
		}
		buffer = reader.readLine();
		return new FixedLenRow(lengths, parts);
	}

	/**
	 * @param format
	 */
	public RowReaderFixedLen(int[] lengths) {
		this.lengths = lengths;
	}

}
