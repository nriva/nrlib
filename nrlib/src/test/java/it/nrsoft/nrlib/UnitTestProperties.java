package it.nrsoft.nrlib;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.nrsoft.nrlib.util.*;
import static org.junit.Assert.*;

public class UnitTestProperties {
	
	@Test
	public final void test1() throws Exception
	{
		Properties prop = new Properties();

		prop.addMulipleValuePropName("query");
		prop.loadProperties(UnitTestProperties.class.getResourceAsStream("/test.properties"));
		List<Map<String,String>> values = prop.getMultipleValueProp("query");
		
/*		
		query.1.type=BOOLEAN
		query.1.test=/page[@version='4.0']
		query.1.group=Versione 4
		query.2.type=BOOLEAN
		query.2.test=/page[@version='3.0']
		query.2.group=Versione 3
*/		
		assertEquals(2, values.size());
		assertEquals("BOOLEAN", values.get(0).get("type"));
		assertEquals("/page[@version='4.0']", values.get(0).get("test"));
		assertEquals("Versione 4", values.get(0).get("group"));
		assertEquals("BOOLEAN", values.get(1).get("type"));
		assertEquals("/page[@version='3.0']", values.get(1).get("test"));
		assertEquals("Versione 3", values.get(1).get("group"));

	}

}
