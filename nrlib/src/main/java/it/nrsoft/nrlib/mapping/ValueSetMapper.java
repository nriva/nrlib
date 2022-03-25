package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptException;


public class ValueSetMapper {
	
	
	public static void map(Map<String,Object> input,Map<String,Object> output,List<MappingRule> rules) throws ScriptException
	{
		for(MappingRule rule : rules)
			rule.compute(input, output);

	}
	

	
	
	public static void map(Map<String,Object> input,Map<String,Object> output)
	{
		for(Entry<String, Object> entry : input.entrySet())
			output.put(entry.getKey(), entry.getValue());
	}	

}
