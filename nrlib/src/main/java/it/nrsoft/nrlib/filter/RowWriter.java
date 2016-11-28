package it.nrsoft.nrlib.filter;

import java.io.IOException;

public interface RowWriter extends RowAccess {

	boolean write(Row row) throws IOException;

}
