package it.nrsoft.nrlib.argparser;

import java.util.List;
import java.util.Map;
import junit.framework.TestCase;



public class ArgParserUnitTest extends TestCase {
	
	ArgParser parser;
	
	public ArgParserUnitTest(String name)
	{
		super(name);
	}
	

	public void setUp() throws Exception {
		
		parser = new ArgParser();
		
		parser.addSwitchChar("-");
		parser.addSwitchChar("--");
		parser.addSwitchChar("/");
		
		// x � uno switch multivalore
		parser.addSwitchDef(new String[] {"x","xxx"}, SwitchDefType.stValued,true,"Valued multivalue switch");
		// b � uno switch semplice
		parser.addSwitchDef(new String[] {"b","bbb"},"Boolean switch");
		// y � uno switch con un solo valore
		parser.addSwitchDef(new String[] {"y","yyy"},SwitchDefType.stValued,"Valued switch");
		
	}	


	public final void testAllOk() throws InvalidSwitchException {
		
		String[] args = new String[] {"-x=x1", "--xxx=x2", "/b", "/y=y1", "arg1", "arg2", "arg3"};
		
		parser.setMinNumArgs(2);

		parser.parse(args);
		
		Map<String, Switch> switches = parser.getSwitches();
		
		assertNotNull("switches != null",switches);
		
		Switch xvalues = switches.get("x");
		
		assertNotNull("xvalues != null",xvalues);
		
		assertEquals("xvalues.getValues().size()", 2, xvalues.getValues().size());
		
		assertEquals("xvalues.getValues().get(0)=x1", "x1", xvalues.getValues().get(0));
		assertEquals("xvalues.getValues().get(1)=x1", "x2", xvalues.getValues().get(1));
		
		Switch bvalues = switches.get("b");
		assertNotNull("bvalues != null",bvalues);
		
		Switch yvalues = switches.get("y");
		assertNotNull("yvalues != null",yvalues);
		
		assertEquals("yvalues.getValues().size()", 1, yvalues.getValues().size());
		
		assertEquals("yvalues.getValues().get(0)=y1", "y1", yvalues.getValues().get(0));
		
			
		List<String> arguments = parser.getArguments();
		
		assertNotNull("arguments != null",arguments);
		
		assertEquals("arguments.size()", 3, arguments.size());
		
		assertEquals("arguments.get(0)=arg1", "arg1", arguments.get(0));
		assertEquals("arguments.get(1)=arg2", "arg2", arguments.get(1));
		assertEquals("arguments.get(2)=arg2", "arg3", arguments.get(2));
		

	}


	public final void testKOBoolean() {
		
		String[] args = new String[] {"/b=true"};
	
		try {
			parser.parse(args);
			fail("Invalid boolean switch");
		} catch (InvalidSwitchException e) {
			
		}
	
	}
	

	public final void testKOMissingValue()
	{
		String[] args = new String[] {"/y"};
		try {
			parser.parse(args);
			fail("Missing value");
		} catch (IllegalArgumentException e) {
			
		} catch (InvalidSwitchException e) {
			
			fail("Wrong exception: " + e.getMessage());
		}
		
	}
	

	public final void testKOMultipleValue()
	{
		String[] args = new String[] {"/y=y1","/y=y2"};
		try {
			parser.parse(args);
			fail("No multiple values allowed");
		} catch (InvalidSwitchException e) {
			
		}
		
	}	
	

	public final void testKOMissingArguments()  {
		
		String[] args = new String[] {"arg1", "arg2"};
		
		parser.setMinNumArgs(3);
		
		try {
			parser.parse(args);
		} catch (InvalidSwitchException e) {
			fail("Wrong exception: " + e.getMessage());
			
		} catch (IllegalArgumentException e) {
			
		}
		
		
	}

}
