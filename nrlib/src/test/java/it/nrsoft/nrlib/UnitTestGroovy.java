package it.nrsoft.nrlib;



import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import it.nrsoft.nrlib.script.CachedScriptProvider;
import it.nrsoft.nrlib.script.ScriptEngine;
import it.nrsoft.nrlib.script.ScriptEngineEnviron;
import it.nrsoft.nrlib.script.ScriptEngineEnvironSimple;
import it.nrsoft.nrlib.script.ScriptProvider;
import it.nrsoft.nrlib.script.ScriptTextLoader;
import it.nrsoft.nrlib.script.groovy.ScriptEngineGroovy;

public class UnitTestGroovy {
	
	

	@Test
	public final void testFile() throws IOException
	
	{
		
		ScriptProvider provider = new CachedScriptProvider();
		ScriptEngine se = new ScriptEngineGroovy(provider );
		
		BufferedReader reader = new BufferedReader(new InputStreamReader( UnitTestGroovy.class.getResourceAsStream("/prova.groovy")));
		
		
		ScriptTextLoader loader = new ScriptTextLoader();
		provider.setScriptText("script1", loader.loadScript(reader));
		
		ScriptEngineEnviron environ = new ScriptEngineEnvironSimple();
		Object o= se.execute("script1", environ );
		
		
		assertTrue("return type", o instanceof Boolean);
		
		Boolean b = (Boolean)o;
		
		assertTrue("return value", b.booleanValue());
		
		
		

	}


	


	@Test
	public final void test() {
		ScriptProvider provider = new CachedScriptProvider();
		
		provider.setScriptText("script1", "return 'pippo'");
		provider.setScriptText("script2", "z=x+y");
		provider.setScriptText("script3", "l=[1,2,3]");
		
		provider.setScriptText("script4", "def name='World'; return 'Hello $name!'");   // Errato
		provider.setScriptText("script5", "def name='World'; return \"Hello $name!\"");	// Corretto
		
		ScriptEngineGroovy se = new ScriptEngineGroovy(provider );
		
		ScriptEngineEnviron environ = new ScriptEngineEnvironSimple();
		
		Object o;
		o=se.execute("script1", environ);
		
		assertTrue("Type check/1", o instanceof String);
		assertEquals("Value check/1", "pippo", o);
		
		environ.put("x", 1);
		environ.put("y", 2);
		environ.put("z", 0);
		se.execute("script2", environ);
		o=environ.get("z");
		
		assertTrue("Type check/2", o instanceof Integer);
		assertEquals("Value check/2", 3, o);
		
		environ.put("l", null);
		se.execute("script3", environ);
		o=environ.get("l");
		assertTrue("Type check/3", o instanceof ArrayList<?>);
		ArrayList<Integer> a = (ArrayList<Integer>)o;
		assertEquals("Length check/3", 3, a.size());
		assertEquals("Value check/3.1", 1, a.get(0).intValue());
		assertEquals("Value check/3.2", 2, a.get(1).intValue());
		assertEquals("Value check/3.3", 3, a.get(2).intValue());
		
		String t1 = "Hello $name!";		// Errato
		String t2 = "Hello World!";		// Corretto
		
		assertEquals(t1, se.execute("script4", environ).toString());
		assertEquals(t2, se.execute("script5", environ).toString());
	}

}
