package nrapps.fileanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FileAnalyzer {
	
	
	protected AnalysisParam params;
	protected File file;
	private PrintStream stream;
	
    protected long lineNumber = 0;
	
	protected void newLine(String line) throws IOException {
		
	}
	
	protected void writeMatchLine(String line) throws IOException {
		
		stream.println(String.format(params.getMatchfmt(), line));
	}
	
	protected String getMatchPrefix() {
		return String.format(params.getMatchpfx(), file.getName(), lineNumber);
	}
	
	protected void writeLine(String line) throws IOException {
		
		stream.println(line);
	}
	

	
	protected void analyzeFile(AnalysisParam params, File file, PrintStream stream) throws IOException {
	
		setup(params, file, stream);
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
        Pattern pattern = Pattern.compile(params.getExpression());
        lineNumber = 0;
		String line = reader.readLine();
		do {
			if(line!=null) {
				lineNumber++;
				newLine(line);
		        Matcher matcher = pattern.matcher(line);
		        if(matcher.matches()) {
		        	matchingLine(line);
		        }
			}
			line = reader.readLine();
		} while (line!=null);
		
		reader.close();
		

	}
	
	protected void setup(AnalysisParam params, File file, PrintStream stream) {
		this.params = params;
		this.file = file;
		this.stream = stream;
	}


	protected abstract void matchingLine(String line) throws IOException;



}