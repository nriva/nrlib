package it.nrsoft.nrlib.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.nrsoft.nrlib.process.DataRow;
import it.nrsoft.nrlib.process.InitialProperties;
import it.nrsoft.nrlib.process.ProcessData;
import it.nrsoft.nrlib.process.SimpleDataRow;
import it.nrsoft.nrlib.process.Step;
import it.nrsoft.nrlib.process.StepResult;
import it.nrsoft.nrlib.process.transform.GroovyTransformation;
import it.nrsoft.nrlib.process.transform.IdentityTransformation;
import it.nrsoft.nrlib.process.transform.MacroTransformation;
import it.nrsoft.nrlib.process.transform.TransformationStep;
import it.nrsoft.nrlib.script.CachedScriptProvider;
import it.nrsoft.nrlib.script.ScriptProvider;
import it.nrsoft.nrlib.script.groovy.ScriptEngineGroovy;



public class TransformationStepTest {
	

	@Test
	public void testIdentity() {
		InitialProperties initProperties = null;
		TransformationStep step = new TransformationStep("step1", initProperties);
		
		IdentityTransformation t1 = new IdentityTransformation("in1");
		step.addTransformation("out1", t1);
		IdentityTransformation t2 = new IdentityTransformation("in2");
		step.addTransformation("out2", t2);
		
		ProcessData dataIn = createTestCase(step, null);		
		step.setDataIn(dataIn);
		step.run();
		
		checkCase(step);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMAcroInvalid() {
		MacroTransformation t1 = new MacroTransformation("xxxx");
	}
	
	@Test
	public void testMacro() {
		
		
		InitialProperties initProperties = null;
		TransformationStep step = new TransformationStep("step1", initProperties);
		
		MacroTransformation t1 = new MacroTransformation("@now");
		step.addTransformation("out1", t1);
		MacroTransformation t2 = new MacroTransformation("@const('pippo')");
		step.addTransformation("out2", t2);
		MacroTransformation t3 = new MacroTransformation("@user");
		step.addTransformation("out3", t3);
		MacroTransformation t4 = new MacroTransformation("@uniqueId");
		step.addTransformation("out4", t4);
		MacroTransformation t5 = new MacroTransformation("@guid");
		step.addTransformation("out5", t5);
		MacroTransformation t6 = new MacroTransformation("@const(3.14)");
		step.addTransformation("out6", t6);
		
		MacroTransformation t7 = new MacroTransformation("@var(var1)");
		step.addTransformation("out7", t7);
		
		
		ProcessData dataIn = createTestCase(step, null);
		Map<String, Object> variables = new HashMap<>();
		variables.put("var1", "value");
		dataIn.setVariables(variables );
		step.setDataIn(dataIn);
		step.run();
		
		checkMacro(step);
	}
	
	
	
	@Test
	public void testIdentitySpring() {
		
		
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test1.xml");
		
		TransformationStep step = (TransformationStep)context.getBean("step1");
		
		ProcessData dataIn = createTestCase(step, null);		
		step.setDataIn(dataIn);
		step.run();
		
		checkCase(step);

		context.close();
		
		
	}	
	
	@Test
	public void testGroovy() {
		
		InitialProperties initProperties = null;
		TransformationStep step = new TransformationStep("step1", initProperties);
		
		
		ScriptProvider scriptProvider = new CachedScriptProvider();
		
		scriptProvider.setScriptText("script1", "return row.in1");
		scriptProvider.setScriptText("script2", "return row.in2");
		
		
		ScriptEngineGroovy engine = new ScriptEngineGroovy(scriptProvider );
		Map<String, Object> variables = new HashMap<>();
		
		variables.put("counter", 0);
		GroovyTransformation t1 = new GroovyTransformation(engine, "script1" );
		step.addTransformation("out1", t1);
		GroovyTransformation t2 = new GroovyTransformation(engine, "script2");
		step.addTransformation("out2", t2);
		
		ProcessData dataIn = createTestCase(step, variables);
		
		step.setDataIn(dataIn);
		
		step.run();
		
		checkCase(step);		
		

		
	}

	
	
	
	@Test
	public void testGroovy2() {
		
		InitialProperties initProperties = null;
		TransformationStep step = new TransformationStep("step1", initProperties);
		
		
		ScriptProvider scriptProvider = new CachedScriptProvider();
		
		scriptProvider.setScriptText("script1", "counter++; return prefix + row.in1 + suffix");
		scriptProvider.setScriptText("script2", "counter++; return row.in1 + row.in2");
		
		
		ScriptEngineGroovy engine = new ScriptEngineGroovy(scriptProvider );
		Map<String, Object> variables = new HashMap<>();
		variables.put("prefix", "^");
		variables.put("suffix", "§");
		variables.put("counter", 0);
		GroovyTransformation t1 = new GroovyTransformation(engine, "script1" );
		step.addTransformation("out1", t1);
		GroovyTransformation t2 = new GroovyTransformation(engine, "script2");
		step.addTransformation("out2", t2);
		
		ProcessData dataIn = createTestCase(step, variables);
		
		
		
		step.setDataIn(dataIn);
		
		step.run();
		
		assertEquals(4, variables.get("counter") ); // _counter++ is called 4 times
		
		checkCase2(step);		
		

		
	}
	
	private ProcessData createTestCase(TransformationStep step, Map<String, Object> variables) {
		ProcessData dataIn = step.createProcessData();
		dataIn.setVariables(variables);
		
		Map<String, Object >_row = new HashMap<>();
		
		_row.put("in1", "pippo1");
		_row.put("in2", "pluto1");

		
		DataRow row = new SimpleDataRow();
		row.setRowValues(_row);
		dataIn.addDataRow(row );
		
		_row = new HashMap<>();
		
		_row.put("in1", "pippo2");
		_row.put("in2", "pluto2");

		
		row = new SimpleDataRow();
		row.setRowValues(_row);
		dataIn.addDataRow(row );
		return dataIn;
	}
	
	
	private void checkCase(Step step) {
		StepResult result = step.getLastResult();
		
		assertFalse(result.isError());
		ProcessData dataOut = result.getDataOut();
		assertNotNull(dataOut);
		
		
		List<DataRow> rows = dataOut.getDataRows();
		assertNotNull(rows);
		assertEquals(2, rows.size());
		
		
		DataRow _row = rows.get(0);
		
		assertEquals("pippo1",  _row.get("out1"));
		assertEquals("pluto1",  _row.get("out2"));
		
		_row = rows.get(1);
		
		assertEquals("pippo2",  _row.get("out1"));
		assertEquals("pluto2",  _row.get("out2"));
	}
	

	private void checkCase2(Step step) {
		StepResult result = step.getLastResult();
		
		assertFalse(result.isError());
		ProcessData dataOut = result.getDataOut();
		assertNotNull(dataOut);
		
		List<DataRow> rows = dataOut.getDataRows();
		assertNotNull(rows);
		assertEquals(2, rows.size());
		
		
		DataRow _row = rows.get(0);
		
		assertEquals("^pippo1§",  _row.get("out1"));
		assertEquals("pippo1pluto1",  _row.get("out2"));
		
		_row = rows.get(1);
		
		assertEquals("^pippo2§",  _row.get("out1"));
		assertEquals("pippo2pluto2",  _row.get("out2"));
	}
	
	private void checkMacro(Step step) {
		StepResult result = step.getLastResult();
		
		assertFalse(result.isError());
		ProcessData dataOut = result.getDataOut();
		assertNotNull(dataOut);
		
		List<DataRow> rows = dataOut.getDataRows();
		assertNotNull(rows);
		assertEquals(2, rows.size());
		
		DataRow _row = rows.get(0);
		
		assertEquals("pippo",  _row.get("out2"));
		assertEquals(System.getProperty("user.name"),  _row.get("out3"));
		System.out.println(_row.get("out4"));
		System.out.println(_row.get("out5"));
		assertEquals(3.14,  _row.get("out6"));
		assertEquals("value",  _row.get("out7"));
		
		_row = rows.get(1);
		
		assertEquals("pippo",  _row.get("out2"));
		assertEquals(System.getProperty("user.name"),  _row.get("out3"));
		System.out.println(_row.get("out4"));
		System.out.println(_row.get("out5"));
		assertEquals(3.14,  _row.get("out6"));
		assertEquals("value",  _row.get("out7"));
		
	}
	

	


}
