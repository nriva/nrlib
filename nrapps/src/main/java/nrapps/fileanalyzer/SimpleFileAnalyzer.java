package nrapps.fileanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleFileAnalyzer extends FileAnalyzer {

	@Override
	protected void matchingLine(String line) throws IOException {
		writeMatchLine(line);
		
	}


}
