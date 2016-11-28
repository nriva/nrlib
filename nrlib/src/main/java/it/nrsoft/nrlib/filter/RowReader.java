package it.nrsoft.nrlib.filter;

import java.io.IOException;

public interface RowReader extends RowAccess {
	

	boolean hasMoreRow();
	
	Row read() throws IOException;

	

}
