package it.nrsoft.nrlib;



import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.Test;

import groovy.util.ScriptException;
import it.nrsoft.nrlib.mapping.MappingRule;
import it.nrsoft.nrlib.mapping.MappingRuleParser;
import it.nrsoft.nrlib.mapping.MappingRulesEvaluator;


public class UnitTestMapping {

	MappingRuleParser parser = new MappingRuleParser();
	Map<String, Object> input = new TreeMap<String, Object>();
	Map<String, Object> output = new TreeMap<String, Object>();

	@Test
	public final void testConcact() throws IOException, ScriptException, javax.script.ScriptException {
		
		MappingRule rule = parser.parse("x=@concat(a,b,c)");
		
		input.clear();
		output.clear();
		
		input.put("a", "1");
		input.put("b", "2");
		input.put("c", "3");
		
		rule.compute(input, output);
		
		
		assertEquals("123", output.get("x"));
	}
	
	@Test
	public final void testUsername() throws IOException, ScriptException, javax.script.ScriptException
	{
		MappingRule rule = parser.parse("x=@user");
		
		input.clear();
		output.clear();
		
		rule.compute(input, output);
		
		
		assertEquals(System.getProperty("user.name"), output.get("x"));
		
	}
	

	public final void testSysprop() throws IOException, ScriptException, javax.script.ScriptException
	{
		MappingRule rule = parser.parse("x=@sys(user.name)");
		
		input.clear();
		output.clear();
		
		rule.compute(input, output);
		
		
		assertEquals(System.getProperty("user.name"), output.get("x"));
		
	}	
	

	public final void testConst() throws IOException, ScriptException, javax.script.ScriptException
	{
		MappingRule rule = parser.parse("x=@const(pippo)");
		
		input.clear();
		output.clear();
		
		rule.compute(input, output);
		
		
		assertEquals("pippo", output.get("x"));
		
	}	
	
	

	public final void testId() throws IOException, ScriptException, javax.script.ScriptException
	{
		MappingRule rule = parser.parse("x=@id");
		
		input.clear();
		output.clear();
		
		rule.compute(input, output);
		assertEquals(1L, output.get("x"));
		
		rule.compute(input, output);
		assertEquals(2L, output.get("x"));
		
		rule.compute(input, output);
		assertEquals(3L, output.get("x"));
	}
	

	public final void testNow() throws IOException, ScriptException, javax.script.ScriptException
	{
		MappingRule rule = parser.parse("x=@now");
		
		input.clear();
		output.clear();
		
		rule.compute(input, output);
		Calendar c = (Calendar) output.get("x");
		assertNotNull(c);
		
		rule.compute(input, output);
		assertEquals(c, output.get("x"));
		
		rule.compute(input, output);
		assertEquals(c, output.get("x"));
	}	
	
	

	public final void testScriptFile() throws IOException, ScriptException, javax.script.ScriptException
	{
		MappingRule rule = parser.parse("x=@script(test)");
		
		input.clear();
		output.clear();
		
	
		rule.compute(input, output);
		assertEquals("goofy", output.get("x"));
		
	}	
	
	

	public final void testCall() throws IOException, ScriptException, javax.script.ScriptException
	{
		MappingRulesEvaluator evaluator = new MappingRulesEvaluator();
		evaluator.load(new BufferedReader(new FileReader("test.mapping")));
		
		input.clear();
		output.clear();
		
//		output.put("x", 0);
		
		input.put("c1", 1);
		input.put("c2", 3);
		input.put("c3", 7);
		
		
		evaluator.evaluateAll(input, output);
		
		System.out.println(output.get("x"));
		System.out.println(output.get("y"));
		
	}		
	
	

}
