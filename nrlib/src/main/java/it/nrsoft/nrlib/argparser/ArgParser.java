package it.nrsoft.nrlib.argparser;

import java.util.*;
import java.util.Map.Entry;

public class ArgParser {
	
	
	private List<String> arguments = new LinkedList<String>();
	
	private Map<String,SwitchDef> switchDefs = new HashMap<String,SwitchDef>();
	
	private List<String> switchChars = new ArrayList<String>();
	private String valueSep = "="; 
	
	private Map<String,Switch> switches = new HashMap<String,Switch>();
	
	public ArgParser() 
	{
	}
	
	public ArgParser(int minNumArgs) 
	{
		this.minNumArgs = minNumArgs;
	}
	
	
	private int minNumArgs = 0;
	
	
	
	/**
	 * @return the switches
	 */
	public Map<String, Switch> getSwitches() {
		return switches;
	}




	/**
	 * @return the arguments
	 */
	public List<String> getArguments() {
		return arguments;
	}



	public SwitchDef addSwitchDef(String[] names)
	{
		
		return addSwitchDef(names,SwitchDefType.stSimple,false,"");
		
	}
	
	public SwitchDef addSwitchDef(String[] names, SwitchDefType switchDefType)
	{
 
		return addSwitchDef(names,switchDefType,false,"");
	}
	
	public SwitchDef addSwitchDef(String[] names, SwitchDefType switchDefType, boolean multiValue)
	{
		return addSwitchDef(names,switchDefType,multiValue,"");
	}
	
	public SwitchDef addSwitchDef(String[] names,String description)
	{
		
		return addSwitchDef(names, SwitchDefType.stSimple,false,description);
		
	}
	
	public SwitchDef addSwitchDef(String[] names, SwitchDefType switchDefType,String description)
	{
 
		return addSwitchDef(names,switchDefType,false,description);
	}
	
	public SwitchDef addSwitchDef(String[] names, SwitchDefType switchDefType, boolean multiValue,String description)
	{
		SwitchDef def = new SwitchDef(names, switchDefType, multiValue,description);
		for(String name : names)
			switchDefs.put(name, def);
		return def;
	}	
	
	

	public void parse(String[] args) throws InvalidSwitchException {

		String name="";
		String value="";
		String switchChar="";
		for(String arg : args)
		{
			
			if((switchChar=findSwitchChar(arg))!="")
			{
				int i=arg.indexOf(valueSep);
				if(i>=0)
				{
					name=arg.substring(switchChar.length(),i);
					value=arg.substring(i+1);
				}
				else
				{
					name=arg.substring(switchChar.length());
					value="";
				}
				
				if(switchDefs.containsKey(name))
				{
					SwitchDef switchDef = switchDefs.get(name);
					Switch _switch;
					
					if(switches.containsKey(switchDef.getNames().get(0)))
					{
						if(switchDef.isMultiInstance())
						{
							_switch=switches.get(switchDef.getNames().get(0));
						}
						else
							throw new InvalidSwitchException(name + " non � uno switch multivalore");
					}
					else
					{
						_switch = new Switch(switchDef.getNames().get(0));
						switches.put(_switch.getName(),_switch);
					}
					
					if(switchDef.getSwitchType()==SwitchDefType.stSimple && !value.equals(""))
						throw new InvalidSwitchException(name +  " non ammette valore " + value);
					if(!value.trim().equals(""))
						_switch.getValues().add(value);
						
				}
				else
					throw new InvalidSwitchException(name);
				
			}
			else
				arguments.add(arg);
		}
		
		if(arguments.size()< minNumArgs)
			throw new IllegalArgumentException("Not enough arguments provided: expected " + minNumArgs + " but was " + arguments.size());
		
		
		for(Switch s : switches.values())
		{
			SwitchDef sf = switchDefs.get(s.getName());
			if(s.getValues().size()==0 && sf.getSwitchType()==SwitchDefType.stValued)
			{
				throw new IllegalArgumentException("No values specified for switch " + s.getName());
			}
		}
		
	}



	/**
	 * @return the minNumArgs
	 */
	public int getMinNumArgs() {
		return minNumArgs;
	}

	/**
	 * @param minNumArgs the minNumArgs to set
	 */
	public void setMinNumArgs(int minNumArgs) {
		this.minNumArgs = minNumArgs;
	}

	private String findSwitchChar(String arg) {
		String switchChar="";
		for(String _switchChar : switchChars )
			if(arg.startsWith(_switchChar))
				switchChar = new String(_switchChar);
		return switchChar;
	}



	/**
	 * @return the switchChar
	 */
	public List<String> getSwitchChars() {
		
		List<String> l = new ArrayList<String>(switchChars.size());
		l.addAll(switchChars);
		return l;
	}
	
	public void addSwitchChar(String switchChar)
	{
		switchChars.add(switchChar);
	}
	
	public void addSwitchChar(char switchChar)
	{
		switchChars.add( String.valueOf(switchChar));
	}
	
	public void addSwitchChars(char[] switchChars)
	{
		for(char switchChar : switchChars)
			addSwitchChar(switchChar);
	}
	




	/**
	 * @return the valueSep
	 */
	public String getValueSep() {
		return valueSep;
	}

	/**
	 * @param valueSep the valueSep to set
	 */
	public void setValueSep(String valueSep) {
		this.valueSep = valueSep;
	}
	
	public String usage()
	{
		String usage="";
		
		Set<String> entries = new HashSet<String>();
		
		for(Entry<String, SwitchDef> entry : switchDefs.entrySet())
		if(!entries.contains(entry.getValue().getNames().get(0)))
		{
			entries.add(entry.getValue().getNames().get(0));
			usage += entry.getValue().buildDescription(switchChars.get(0), valueSep)+"\r\n";
			
		}
		
		return usage;
	}
	
	

}
