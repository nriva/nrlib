package nrapps.jspanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import it.nrsoft.nrlib.io.FileSystemWalkerListener;


public class WaxJspAnalyzer implements FileSystemWalkerListener {
	
	   private static final Logger logger = 
		       Logger.getLogger(WaxJspAnalyzer.class);
	

	@Override
	public void visitFile(File file) {
		 
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		
			String line = reader.readLine();
			boolean formRead = false;
			
			while(line!=null)
			{
				line = line.trim();
				if(line.length()>0)
				{
					if(formRead)
					{
				        String pattern = "<%@ include file=\"\\./\\.\\./(\\w+)/templates/([\\w_]+)\\.jsp\"%>";

				        // Create a Pattern object
				        Pattern r = Pattern.compile(pattern);

				        // Now create matcher object.
				        Matcher m = r.matcher(line);

				        if (m.find()) {
				        	String filename = file.getName().substring(0,2) + file.getName().substring(4,7);
				            System.out.println(filename + ","  + m.group(1) + "," + m.group(2));
				        }					
						
						break;
						
					}
					if(line.equalsIgnoreCase("<form>"))
						formRead = true;
				}
				
				line = reader.readLine();
			}
		
		} catch (FileNotFoundException e) {
			logger.error("File not found", e);
		} catch (IOException e) {
			logger.error("I/O Error", e);
		}
		finally
		{
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					logger.warn("Error in closing file", e);
				}
		}

		
	}

}
