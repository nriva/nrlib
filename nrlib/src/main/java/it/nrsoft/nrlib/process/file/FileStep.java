package it.nrsoft.nrlib.process.file;

import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.Step;

public abstract class FileStep extends Step {
	
	
	public static final String PROP_FILE_NAME = "file.name";
	
	protected String fileName ="";

	public FileStep(String name, InitialProperties properties) {
		super(name, properties);
		
		fileName = properties.getProperty(PROP_FILE_NAME);

	}


	

}
