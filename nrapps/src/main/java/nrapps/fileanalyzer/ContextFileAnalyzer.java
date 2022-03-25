package nrapps.fileanalyzer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Deque;
import java.util.LinkedList;

import it.nrsoft.nrlib.util.Util;

public class ContextFileAnalyzer extends FileAnalyzer {

	private static final String MARKER_BEFORE = "-";
	private static final String MARKER_AFTER = "+";
	
	private String markerBefore = MARKER_BEFORE;
	private String markerAfter = MARKER_AFTER;
	
	private static final int MAX_CONTEXT_SIZE = 10;
	private static final int QUEUE_SIZE = 10;
	private Deque<String> queue = new LinkedList<>();
	private boolean matched;
	
	private int contextLines = 0;
	private Integer contextLenBefore = 1;
	private Integer contextLenAfter = 1;
	
	boolean firstWrite=true;
	
	
	@Override
	protected void newLine(String line) throws IOException {

		if(queue.size()>QUEUE_SIZE)
			queue.removeFirst();
		
		queue.add(line);
		
		if(contextLines>0) {
			writeLine(markerAfter + line);
			contextLines--;
			if(contextLines==0)
				matched = false;
		} else {
			matched = false;
		}
		
	}

	 

	@Override
	protected void matchingLine(String line) throws IOException {
		
		String lineToWrite;
		
		if(!matched) {
			
			
			if(firstWrite) {
				lineToWrite = "";
				firstWrite = false;
			} else {
				lineToWrite = System.lineSeparator();
			}
			writeLine(lineToWrite + getMatchPrefix());
			
			String[] lines = new String[queue.size()];
			queue.toArray(lines);
			// the last position of lines contains the current line
			int intervalLen = Util.min(lines.length-1, contextLenBefore);
			int start=lines.length-1-intervalLen;
			if(start<0) start=0;
			int stop=start+intervalLen-1;
			for(int i=start;i<=stop;i++) {
				writeLine(markerBefore + lines[i]);
			}
		} else {
			// I am in an overlapping context!
		}
		
		writeMatchLine(line);
		
		contextLines = contextLenAfter;
		matched = true;
		
	}



	@Override
	protected void setup(AnalysisParam params, File file, PrintStream stream) {
		super.setup(params, file, stream);
		
		if(params.getAnalyzerParams()!=null && params.getAnalyzerParams().size()>0)
		{
			for(String param: params.getAnalyzerParams()) {
				
				String[] parts = param.split(":");
				switch(parts[0]) {
				case "beforeLen":
					contextLenBefore = Util.min(Integer.valueOf(parts[1]), MAX_CONTEXT_SIZE);
					break;
				case "afterLen":
					contextLenAfter = Util.min(Integer.valueOf(parts[1]), MAX_CONTEXT_SIZE);
					break;
					
				case "beforeMarker":
					markerBefore = parts[1];
					break;
				case "afterMarker":
					markerAfter = parts[1];
					break;
				}
				
			}
		}
	}


}
