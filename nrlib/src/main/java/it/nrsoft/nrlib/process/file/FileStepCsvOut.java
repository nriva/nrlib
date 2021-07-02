package it.nrsoft.nrlib.process.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.StepResult;

public class FileStepCsvOut extends FileStepCsv {




	public FileStepCsvOut(String name,InitialProperties properties) {
		super(name, properties);
	}



	@Override
	public StepResult execute() {
		
		StepResult result = new StepResult();
		
		ProcessData dataOut = new ProcessData(properties);

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			
			boolean headerWritten = false;

			for(DataRow row: dataIn.getDataRows()) {
				
				String line = "";
				
				if(header && !headerWritten) {
					for(String key: row.keySet()) {
						if(line.length()>0) line += separator;
						line += key.toString();						
						
					}
					bw.write(line+"\r\n");	
					headerWritten = true;
					line="";
				}
				
			
				
				
				for(Object value: row.values()) {
					if(line.length()>0) line += separator;
					line += value.toString();
					
				}
			
				bw.write(line+"\r\n");
				
				dataOut.addDataRow(row);
			}
			
			
			
			result.setDataOut(dataOut);
			
			bw.close();
			
		} catch (IOException e) {
		
			result.setErrorCode(1);
			result.setMessage(e.getMessage());
		}
		
		return result;
	}


}
