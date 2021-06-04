package nrapps.fileanalyzer;

public class FileAnalyzerFactory {
	
	public static FileAnalyzer create(AnalysisParam params) {
		
		FileAnalyzer analyzer  = null;
		switch(params.getAnalyzerType()) {
		case "simple":
			analyzer = new SimpleFileAnalyzer();
			break;
		case "context":
			analyzer = new ContextFileAnalyzer();
			break;
			
		default:
			throw new IllegalArgumentException( "\"" + params.getAnalyzerType() +  "\" is not a valid analyzer type");
		}
		return analyzer;
		
	}

}
