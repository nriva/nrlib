package it.nrsoft.nrlib.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class ScriptTextLoader {
	
	public String loadScript(BufferedReader reader) throws IOException {

		String myscript="";
		String line = reader.readLine();
		
		while(line!=null)
		{
			myscript += line + " ";
			line = reader.readLine();
		}
		reader.close();		
		return myscript;
		
	}
	
	public String loadScript(String filename) throws IOException {
		
		File file = new File(filename);
		return loadScript(file);
	}
	
	public String loadScript(File file) throws IOException {
		
		Path path = file.toPath();
		
		if(file.exists() && file.isFile()) {
			
			String scriptId = path.getFileName().toString(); 
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			return loadScript(reader);
		}

		else {
			throw new IllegalArgumentException("file not valid");
		}
		
		
		
		
	}
	
	

}
