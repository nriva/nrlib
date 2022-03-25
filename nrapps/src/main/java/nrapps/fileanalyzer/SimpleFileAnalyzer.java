package nrapps.fileanalyzer;

import java.io.IOException;

public class SimpleFileAnalyzer extends FileAnalyzer {

	@Override
	protected void matchingLine(String line) throws IOException {
		writeMatchLine(line);
		
	}


}
