package it.nrsoft.nrlib.filter;

import java.io.*;

public interface RowAccess {
	
	void open(FileFilterActionContext context) throws IOException;
	void close(FileFilterActionContext context) throws IOException;

}
