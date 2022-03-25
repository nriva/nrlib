package it.nrsoft.nrlib.filter;

import java.io.*;

public class FileFilter {
	
	
	private FileFilterActionContext context = new FileFilterActionContext();
	
	private FileFilterAction action = null;
	
	private RowReader reader;
	


	public FileFilterAction getAction() {
		return action;
	}


	public void setAction(FileFilterAction action) {
		this.action = action;
	}


	private RowWriter writer;
	
	
	/**
	 * @return the reader
	 */
	public RowReader getReader() {
		return reader;
	}


	/**
	 * @param reader the reader to set
	 */
	public void setReader(RowReader reader) {
		this.reader = reader;
	}


	/**
	 * @return the writer
	 */
	public RowWriter getWriter() {
		return writer;
	}


	/**
	 * @param writer the writer to set
	 */
	public void setWriter(RowWriter writer) {
		this.writer = writer;
	}


	public void perform() throws IOException
	{
		reader.open(context);
		writer.open(context);
		
		while(reader.hasMoreRow())
		{
			Row row = reader.read();
			context.setCurrLine(context.getCurrLine()+1);
			FileFilterActionResult result = action.perform(context,row);
			if(!result.equals(FileFilterActionResult.SKIP))
			{
				writer.write(row);
			}
		}
		reader.close(context);
		writer.close(context);
		
	}


	/**
	 * @param reader
	 * @param writer
	 */
	public FileFilter(RowReader reader, RowWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}




}
