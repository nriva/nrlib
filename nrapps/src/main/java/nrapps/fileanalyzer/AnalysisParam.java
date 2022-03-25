package nrapps.fileanalyzer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.nrsoft.nrlib.argparser.Switch;

public class AnalysisParam {
	
	private String expression;
	
	private String inputFileName="";
	
	private List<String> inputFileNameList=null;
	
	private String outputFileName="output.txt";
	
	private String analyzerType="simple";

	private List<String> analyzerParams;

	private boolean stdOut;

	private String matchfmt = "%1$s";

	private String matchpfx = "%1$s@%2$d";
	
	private List<String> folders=null;
	
	private boolean recursive = false;
	
	private String filenamePattern = "";

	public String getInputFileName() {
		return inputFileName;
	}


	public String getOutputFileName() {
		return outputFileName;
	}


	public String getExpression() {
		return expression;
	}


	public List<String> getAnalyzerParams() {
		return analyzerParams;
	}


	public AnalysisParam(List<String> args, Map<String, Switch> switches) {

		if(switches.containsKey("o")) {
			outputFileName = switches.get("o").getValues().get(0);
		}
		
		if(switches.containsKey("s")) {
			stdOut = true; 
		}
		if(switches.containsKey("m")) {
			matchfmt = switches.get("m").getValues().get(0);
		}
		if(switches.containsKey("x")) {
			matchpfx = switches.get("x").getValues().get(0);
		}
		
		if(switches.containsKey("a")) {
			analyzerType = switches.get("a").getValues().get(0);
		}
		if(switches.containsKey("p")) {
			analyzerParams = switches.get("p").getValues();
		}
		
		if(switches.containsKey("r")) {
			recursive = true; 
		}
		
		if(switches.containsKey("f")) {
			filenamePattern = switches.get("f").getValues().get(0); 
		}
		
		if(switches.containsKey("d")) {
			folders = switches.get("d").getValues();
		}
		
		expression = args.get(0);
		if(args.size()>2) {
			inputFileNameList = new LinkedList<>();
			for(int i=1;i<args.size();i++)
				inputFileNameList.add(args.get(i));
			
		} else {
			inputFileName = args.get(1);
		}

	}


	public List<String> getInputFileNameList() {
		return inputFileNameList;
	}


	public String getMatchpfx() {
		return matchpfx;
	}


	public boolean isStdOut() {
		return stdOut;
	}


	public String getMatchfmt() {
		return matchfmt;
	}
	
	public String getAnalyzerType() {
		return analyzerType;
	}


	public List<String> getFolders() {
		return folders;
	}


	public boolean isRecursive() {
		return recursive;
	}


	public String getFilenamePattern() {
		return filenamePattern;
	}
	

}
