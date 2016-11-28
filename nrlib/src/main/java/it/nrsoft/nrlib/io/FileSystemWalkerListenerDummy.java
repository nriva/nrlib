package it.nrsoft.nrlib.io;

import java.io.File;

public class FileSystemWalkerListenerDummy implements FileSystemWalkerListener {

	public void visitFile(File file) {
		System.out.println(file.getAbsolutePath());

	}

}
