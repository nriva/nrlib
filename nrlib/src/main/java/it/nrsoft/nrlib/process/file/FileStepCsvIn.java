package it.nrsoft.nrlib.process.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.StepResult;


public class FileStepCsvIn extends FileStepCsv {

	public FileStepCsvIn(String name, InitialProperties properties) {
		super(name, properties);
	}

	@Override
	public StepResult execute() {
		StepResult result = new StepResult();
		
		ProcessData dataOut = new ProcessData(properties);

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			boolean headerRead = false;
			
			
			DataRow row;
			
			String line = br.readLine();
			if(line==null) line="";
			line = line.trim();
			
			String[] fields = null;
			
			while(line.length()>0) {
				String[] parts = line.split(separator);
				
				if(header && !headerRead) {
					headerRead = true;
					fields = parts;
				} else {
					row = new SimpleDataRow();
					if(fields==null) {
						fields = new String[parts.length];
						for(int p=0;p<parts.length;p++)
							fields[p] = "COL" + String.format("%03d", p);
						headerRead = true;
					}
					
					for(int f=0;f<fields.length;f++) {
						row.put(fields[f], parts[f]);
					}
					dataOut.addDataRow(row);
				}
				
				
				line = br.readLine();
				if(line==null) line="";
				line = line.trim();				
				
			}
			
			result.setDataOut(dataOut);
			
			br.close();
			
		} catch (IOException e) {
		
			result.setErrorCode(1);
			result.setMessage(e.getMessage());
		}
		
		return result;	
	}

}
