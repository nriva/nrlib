package it.nrsoft.nrlib.process.file;

import it.nrsoft.nrlib.process.InitialProperties;

public abstract class FileStepCsv extends FileStep {
	
	public static final String PROP_FILE_CSV_SEP = "file.csv.sep";
	public static final String PROP_FILE_CSV_HEADER = "file.csv.header";
	protected boolean header = false;
	protected String separator = ","; 

	public FileStepCsv(String name, InitialProperties properties) {
		super(name, properties);
		header = Boolean.valueOf(properties.getProperty(PROP_FILE_CSV_HEADER, "false"));
		separator = properties.getProperty(PROP_FILE_CSV_SEP, ",");

	}

}
