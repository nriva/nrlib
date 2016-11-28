package it.nrsoft.nrlib.textfilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.nrsoft.nrlib.argparser.Switch;


@Deprecated
public class TextFilter {
	
	private List<String> actions= new LinkedList<String>();
	
	private List<String> streams = new LinkedList<String>();

	private String pattern="";
	
	private boolean overwrite = false;
	

	
	public TextFilter(List<String> arguments,Map<String, Switch> switches) {
		
		if(arguments==null)
			throw new IllegalArgumentException("arguments");
		
		if(switches==null)
			throw new IllegalArgumentException("switches");		
			
		streams.addAll(arguments);
		
	
		
		pattern = switches.get("p").getValues().get(0);
		actions.addAll(switches.get("a").getValues());
		overwrite = switches.containsKey("o");



	}



	public void execute() throws IOException {
		List<TextLine> lines;
		
		for(String streamName : streams)
		{
			lines = loadLines(streamName);
			for(String action : actions)
				perform(action,lines);
			writeLines(lines,streamName+".cpy");
			
			if(overwrite)
			{
				File f = new File(streamName);
				File f_cpy = new File(streamName+".cpy");
				f.delete();
				f_cpy.renameTo(f);
				f_cpy.delete();
			}
		}
	}

	private void writeLines(List<TextLine> lines,String streamName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(streamName));
		
		for(TextLine line : lines)
			if(line.isOutput())
				writer.write(line.getLine()+"\r\n");
		writer.close();

		
	}

	private void perform(String action, List<TextLine> lines) {
		

		ITextLineAction _action = null;
		if(action.startsWith("skip"))
		{

			String[] params = action.substring(5,  action.length()-1).split(",");
			_action = new TextLineActionSkip(pattern,params);	
		}	
		
		if(_action!=null)
			_action.perform(lines);

	}
	
	

	private List<TextLine> loadLines(String streamName) throws IOException {
		List<TextLine> lines = new LinkedList<TextLine>();
		BufferedReader reader = new BufferedReader(new FileReader(streamName));
        String text = null;

        // repeat until all lines is read
        while ((text = reader.readLine()) != null) {
        	lines.add(new TextLine(text));
        	
        }
        reader.close();
		return lines;
	}

}
