package it.nrsoft.nrlib.io;

import java.io.File;

public interface FileSystemWalkerListener {
	
	void visitFile(File file);

}
