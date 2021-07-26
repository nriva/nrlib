package it.nrsoft.nrlib.mapping;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.*;

import javax.script.*;

import org.apache.log4j.Logger;

import it.nrsoft.nrlib.util.StringUtil;



public class MappingRule {
	
	static Logger logger = Logger.getLogger(MappingRule.class.getName());
	
	
	private MappingRulesEvaluator evaluator;
	
	/**
	 * @return the evaluator
	 */
	public MappingRulesEvaluator getEvaluator() {
		return evaluator;
	}

	/**
	 * @param evaluator the evaluator to set
	 */
	public void setEvaluator(MappingRulesEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	static Map<String,MappingMacro> macros = new HashMap<String, MappingMacro>();
	
	static {
		macros.put("@now", new MappingMacroNow());
		macros.put("@id", new MappingMacroUniqueId());
		macros.put("@guid", new MappingMacroGUID());
		macros.put("@user", new MappingMacroUsername());
		macros.put("@concat", new MappingMacroConcat());
		macros.put("@script", new MappingMacroGroovy());
		macros.put("@const", new MappingMacroConst());
		macros.put("@sys", new MappingMacroSystemProp());
	}	
	
	
	String leftValue="";

	public String getLeftValue() {
		return leftValue;
	}

//	public void setLeftValue(String leftValue) {
//		this.leftValue = leftValue;
//	}
	
	Map<String,String> properties = new HashMap<String,String>();
	
	String expression;
	
	List<String> macroParam = new ArrayList<String>();
	
	List<String> callParam = new ArrayList<String>();

//	public Map<String, String> getProperties() {
//		return properties;
//	}

	/**
	 * @return the macroParam
	 */
	public Iterable<String> getMacroParams() {
		return macroParam;
	}
	
	public Iterable<String> getCallParams() {
		return callParam;
	}
	

	public String getExpression() {
		return expression;
	}

	public String getProperty(String property)
	{
		return properties.get(property);
	}
	
	public String getProperty(String property,String defaultValue)
	{
		String value=defaultValue;
		if(properties.containsKey(property))
			value=properties.get(property);
		return value;
	}
	
//	public String setProperty(String property,String value)
//	{
//		properties.put(property,value);
//		return value;
//	}
	

	public Map<String, String> getProperties() {
		return properties;
	}
	
	public void compute(Map<String,Object> input,Map<String,Object> output) throws ScriptException
	{
		if(expression.startsWith("@"))
		{
			
			String macroname = expression;
			
			MappingMacro macro =  macros.get(macroname);
			macro.evaluator = evaluator;
			output.put(leftValue, macro.compute(macroParam,input));				
		}
		else if(expression.startsWith("#"))
		{
			
			Bindings bindings = evaluator.engine.getBindings(ScriptContext.GLOBAL_SCOPE);
			for(Map.Entry<String, Object> entry : input.entrySet())
				bindings.put(entry.getKey(), entry.getValue());
			
			
			String callname = expression.substring(1);
			
			String params = StringUtil.join(callParam, ",");
			
			String call = "_fs_." + callname + "(" + params +  ")";
			
			Object o = evaluator.engine.eval(call);
			
			output.put(leftValue, o);
			
			evaluator.engine.getBindings(ScriptContext.GLOBAL_SCOPE).put(leftValue, o);			
			
		}		
		else if(input.containsKey(expression))
		{
			output.put(leftValue, input.get(expression));
			logger.trace("Rule: " + leftValue + " = input[" + expression  +"] : " + input.get(expression));
		}
		
	}
}
