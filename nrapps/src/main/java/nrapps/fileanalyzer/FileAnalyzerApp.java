package nrapps.fileanalyzer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import it.nrsoft.nrlib.argparser.ArgParser;
import it.nrsoft.nrlib.argparser.InvalidSwitchException;
import it.nrsoft.nrlib.argparser.Switch;
import it.nrsoft.nrlib.argparser.SwitchDefType;

public class FileAnalyzerApp {
	
	public static void main(String[] args) {
		
		FileAnalyzerApp analyzer = new FileAnalyzerApp();
		analyzer.run(args);
		
	}

	private void run(String[] args) {
		
		
		ArgParser parser = new ArgParser();
		setupOptions(parser);
		
		try {
			parser.parse(args);
		} catch (InvalidSwitchException e1) {
			System.out.println("Error in reading parameters" + e1.getMessage());
			System.out.println(parser.usage());
		}

		List<String> arg = parser.getArguments();
		Map<String, Switch> switches = parser.getSwitches();

		AnalysisParam params =  new AnalysisParam(arg,switches);
		
		

		
		
		String outputFilename = params.getOutputFileName();
		File output = new File(outputFilename);
		
		FileAnalyzer fileAnalyzer = FileAnalyzerFactory.create(params);
		try {
			
			PrintStream stream = null;
			if(params.isStdOut())
				stream = System.out;
			else
				stream = new PrintStream(output);
			
			if(params.getInputFileNameList()!=null && params.getInputFileNameList().size()>0) {
				
				for(String filename: params.getInputFileNameList()) {
					File file = new File(filename);
					fileAnalyzer.analyzeFile(params,file, stream);
					
				}
			}
			else
			{
				File file = new File(params.getInputFileName());
				fileAnalyzer.analyzeFile(params,file, stream);
			}
			
			
			
			if(!params.isStdOut())
				stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void setupOptions(ArgParser parser) {
		
		parser.setValueSep("=");
		parser.addSwitchChar("-");
		parser.addSwitchChar("--");
		parser.setMinNumArgs(2);
		parser.addSwitchDef("o", "output", SwitchDefType.stValued, false);
		parser.addSwitchDef("s", "stdout", SwitchDefType.stSimple);
		parser.addSwitchDef("a", "analyzer", SwitchDefType.stValued, false);
		parser.addSwitchDef("p", "param", SwitchDefType.stValued, true, "Analyzer params");
		parser.addSwitchDef("d", "folder", SwitchDefType.stValued, true, "Input Folder");
		parser.addSwitchDef("f", "filepattern", SwitchDefType.stValued, false, "Input File name pattern");
		parser.addSwitchDef("r", "recursive", SwitchDefType.stValued, false, "Recursive");
		parser.addSwitchDef("m", "matchfmt", SwitchDefType.stValued, false, "Match output format");
		parser.addSwitchDef("x", "matchpfx", SwitchDefType.stValued, false, "Match prefix format");
		

		
	}


}
