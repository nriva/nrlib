package it.nrsoft.nrlib.argparser;

import java.util.*;

public class SwitchDef {
	
	private List<String> names=new ArrayList<String>();
	
	private SwitchDefType switchType;
	
	private boolean multiInstance = false;
	
	private String description = "";

	/**
	 * @return the name
	 */
	public List<String> getNames() {
		return names;
	}


	/**
	 * @return the switchType
	 */
	public SwitchDefType getSwitchType() {
		return switchType;
	}

	/**
	 * @param switchType the switchType to set
	 */
	public void setSwitchType(SwitchDefType switchType) {
		this.switchType = switchType;
	}
	
	public SwitchDef(String[] names,SwitchDefType switchType)
	{
		
		this(names, switchType, false);
	}

	/**
	 * @return the multiInstance
	 */
	public boolean isMultiInstance() {
		return multiInstance;
	}

	/**
	 * @param multiInstance the multiInstance to set
	 */
	public void setMultiInstance(boolean multiInstance) {
		this.multiInstance = multiInstance;
	}

	/**
	 * @param name
	 * @param switchType
	 * @param multiInstance
	 */
	public SwitchDef(String[] names, SwitchDefType switchType,
			boolean multiInstance) {
		this(names,switchType, multiInstance,"");
	}
	
	public SwitchDef(String[] names, SwitchDefType switchType,
			boolean multiInstance, String description) {
		
		
		for(String name : names)
			this.names.add(name);
		this.switchType = switchType;
		this.multiInstance = multiInstance;
		this.description = description;
	}	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String buildDescription(String switchChar,String valuesSep)
	{
		String desc = switchChar + names.get(0);
		if(switchType==SwitchDefType.stValued)
			desc += valuesSep+"value";
		
		if(isMultiInstance())
			desc += " [" + desc + "]";
		
		if(!description.equals("")) {
			if(switchType==SwitchDefType.stSimple)
				desc += "\t";
			desc += "\t\t" + description;
		}
		
		return desc;
		
	}
	
	

}
