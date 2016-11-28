package it.nrsoft.nrlib.mapping;

import java.io.*;
import java.util.*;

import javax.script.*;

import org.apache.log4j.Logger;

public class MappingRulesEvaluator {
	
	static Logger logger = Logger.getLogger(MappingRulesEvaluator.class.getName());
	
	private  Map<String,MappingRule> rules = new LinkedHashMap<String,MappingRule>();
	private MappingRuleParser parser = new MappingRuleParser();
	
	private Map<String,Map<String,String>> inputFieldDefinitions = new LinkedHashMap<String,Map<String,String>>();
	private Map<String,Map<String,String>> outputFieldDefinitions = new LinkedHashMap<String,Map<String,String>>();
	
	
	public ScriptEngine engine;
	
	
	public MappingRulesEvaluator()
	{
    	ScriptEngineManager manager = new ScriptEngineManager();
    	engine = manager.getEngineByName("groovy");
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(MappingRule e) {
		return getRules().put(e.leftValue,e)!=null;
	}
	
	public void evaluateAll(Map<String, Object> input, Map<String, Object> output) throws ScriptException
	{
		for(MappingRule rule : getRules().values())
			rule.compute(input, output);
	}
	

	/**
	 * 
	 * @see java.util.List#clear()
	 */
	public void clear() {
		getRules().clear();
	}
	
//	GroovyScriptEngine engine = new GroovyScriptEngine(new URL[] {});
//	GroovyShell shell = new GroovyShell();
	
	public void load(BufferedReader in) throws IOException, javax.script.ScriptException
	{
		
		String line=null;
		String script = "";
		
		int section = 0;
		
		Map<String,Map<String,String>> _outputFieldDefinitions = new LinkedHashMap<String,Map<String,String>>();
		
		
		
		while((line = in.readLine())!=null)
		{
			line = line.trim();
			
			int commentpos = line.indexOf("//");
			if(commentpos>=0)
				line = line.substring(0,commentpos).trim();
			
			if(line.length()>0) {
				
				if(line.equals("input:"))
					section = 1;
				else if(line.equals("output:"))
					section = 2;
				else if(line.equals("rules:"))
					section = 3;
				else if(line.matches("^script\\s+\"\\w+\"\\s*:"))
					section = 4;
				else
				{
					switch(section)
					{
					case 1:
					case 2:
						
						if(line.startsWith("def"))
						{
							String key = line.substring(4, line.indexOf('=')).trim() ;
							String _line = line.substring(line.indexOf('{')+1);
							_line = _line.substring(0, _line.indexOf('}')).trim();
							Map<String,String> props = parseDictionary(_line);

							if(section==1)
								inputFieldDefinitions.put(key, props);
							else if(section==2)
								_outputFieldDefinitions.put(key, props);
							
						}						
						break;
					case 3:
						// Rule
						MappingRule rule = parser.parse(line);
						rule.setEvaluator(this);
						rules.put(rule.leftValue,rule);
						break;
					case 4:
						script += line + "\r\n";
						break;
					}
				}
				



			}
		} // while
		
		if(!script.equals("")) {
			script = "class _ScriptFunctions_ {\r\n"
					+ script + "\r\n"
					+ "}";
			engine.eval(script);
			
			engine.eval("_fs_= new _ScriptFunctions_()");
		}
		
		SortedSet<String> set = new TreeSet<String>();
		
		set.addAll(rules.keySet());
		set.addAll(_outputFieldDefinitions.keySet());

		for(String rulename : set)
			if(_outputFieldDefinitions.containsKey(rulename))
				outputFieldDefinitions.put(rulename, _outputFieldDefinitions.get(rulename));
			else
				outputFieldDefinitions.put(rulename, new TreeMap<String,String>());
	
	}

	/**
	 * @return the inputFieldDefinitions
	 */
	public Map<String, Map<String, String>> getInputFieldDefinitions() {
		return inputFieldDefinitions;
	}

	/**
	 * @return the outputFieldDefinitions
	 */
	public Map<String, Map<String, String>> getOutputFieldDefinitions() {
		return outputFieldDefinitions;
	}

	private Map<String, String> parseDictionary(String _line) throws IOException {
		
		Map<String,String> props = new TreeMap<String,String>();
	       String currKey=null;
	       
	       Reader r = new StringReader(_line);
	       StreamTokenizer st = new StreamTokenizer(r);
	       
       
	       st.ordinaryChar('=');
	       st.ordinaryChar(':');
	       st.ordinaryChar(',');
	       st.quoteChar('"');
	       
	       
	       
	       boolean eof = false;
	       do {

	          int token = st.nextToken();
	          switch (token) {
	             case StreamTokenizer.TT_EOF:
	                eof = true;
	                break;               
	             case StreamTokenizer.TT_EOL:
	                break;
	             case StreamTokenizer.TT_WORD:
	            	 
	            	 if(currKey==null)
	            		 currKey = st.sval;
	            	 else {
	            		 logger.trace("Key: " + currKey + ", Value: " + st.sval);
	            		 props.put(currKey, st.sval);
	            		 currKey = null;
	            	 }
	            	 break;
	             case StreamTokenizer.TT_NUMBER:
	            	 logger.trace("Number: " + st.nval);
	            	 break;
	             default:
	            	 switch((char) token)
	            	 {
	            	 case '"':
	            		 logger.trace("Key: " + currKey + ", Value: " + st.sval);
	            		 props.put(currKey, st.sval);
	            		 currKey = null;
	            		 break;
	            	 }
	            	 
	          }
	       } while(!eof);
	       return props;
	  }

	/**
	 * @return the inputProperties
	 */
//	public Map<String, Map<String, String>> getFieldDefinitions() {
//		return fieldDefinitions;
//	}

	public void load(String filename) throws FileNotFoundException, IOException, ScriptException {

		load(new BufferedReader(new FileReader(filename)));
		
	}

	public Map<String,MappingRule> getRules() {
		return rules;
	}

	public void setRules(Map<String,MappingRule> rules) {
		this.rules = rules;
	}
	
	

}
