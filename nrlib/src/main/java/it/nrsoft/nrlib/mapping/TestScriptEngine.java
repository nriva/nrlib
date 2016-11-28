package it.nrsoft.nrlib.mapping;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class TestScriptEngine {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		FileInputStream str;
		try {
			str = new FileInputStream("log.properties");
			PropertyConfigurator.configure(str);
		} catch (FileNotFoundException e1) {
			BasicConfigurator.configure();
		}
		
		MappingRuleParser parser = new MappingRuleParser();
		MappingRule rule = parser.parse("a=@script(a,b,c);fmt=\"dd/MM/yyyy\";prop1=value1");
		
		
		System.out.println("leftValue=" + rule.getLeftValue());
		System.out.println("expression="+rule.getExpression());
		for(String p : rule.getMacroParams())
			System.out.println("macro param="+p);
		
		for(Entry<String, String> entry : rule.getProperties().entrySet())
		{
			System.out.println(entry.getKey()+"="+entry.getValue());
		}

	}

}
