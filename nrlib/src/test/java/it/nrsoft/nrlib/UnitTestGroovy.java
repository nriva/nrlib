package it.nrsoft.nrlib;



import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import it.nrsoft.nrlib.script.ScriptEngine;
import it.nrsoft.nrlib.script.groovy.ScriptEngineGroovy;



public class UnitTestGroovy {
	
	
	@Test
	public final void testFile() throws IOException
	{
		ScriptEngine se = new ScriptEngineGroovy();
		
		InputStreamReader reader = new InputStreamReader( UnitTestGroovy.class.getResourceAsStream("/prova.groovy"));
		
		se.execute(reader);
	}
	

	@Test
	public final void test() 
	{
		ScriptEngineGroovy se = new ScriptEngineGroovy();
		
		Map<String,Object> environ = new HashMap<String,Object>();
		
		se.setEnviron(environ);
		
		Object o;
		o=se.execute("return 'pippo'");
		
		assertTrue("Type check/1", o instanceof String);
		assertEquals("Value check/1", "pippo", o);
		
		environ.put("x", 0);
		se.execute("x=10");
		o=environ.get("x");
		
		assertTrue("Type check/2", o instanceof Integer);
		assertEquals("Value check/2", 10, o);
		
		environ.put("l", null);
		se.execute("l=[1,2,3]");
		o=environ.get("l");
		
		assertTrue("Type check/3", o instanceof ArrayList<?>);
		ArrayList<Integer> a = (ArrayList<Integer>)o;
		assertEquals("Length check/3", 3, a.size());
		assertEquals("Value check/3.1", 1, a.get(0).intValue());
		assertEquals("Value check/3.2", 2, a.get(1).intValue());
		assertEquals("Value check/3.3", 3, a.get(2).intValue());
		
		environ.put("s", "");
		se.execute("def name='World'; s='Hello $name!'");			// non funziona
		o=environ.get("s");
		assertNotEquals("test/1", "Hello World!",o);
		
		se.execute("def name='World'; s= \"Hello $name!\"");		// funziona 	--> ' vs "
		o=environ.get("s");
		assertEquals("test/2", "Hello World!",o.toString());
		
		
	}

}
