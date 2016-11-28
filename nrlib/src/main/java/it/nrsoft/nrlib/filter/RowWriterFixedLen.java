package it.nrsoft.nrlib.filter;

import java.io.*;

public abstract class RowWriterFixedLen extends FixedLenData implements RowWriter {

	private PrintWriter writer;

	public void open(FileFilterActionContext context) throws IOException {
		writer = getWriter();

	}

	public void close(FileFilterActionContext context) throws IOException {
		writer.close();

	}

	public boolean write(Row row) throws IOException {
		writer.println(row.getString());
		return true;
	}
	
	protected abstract PrintWriter getWriter() throws IOException; 



}
