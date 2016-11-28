package it.nrsoft.nrlib.argparser;

import java.util.*;

public class Switch {
	
	private String name;
	private List<String> values = new LinkedList<String>();
	/**
	 * @param name
	 * @param values
	 */
	public Switch(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the values
	 */
	public List<String> getValues() {
		return values;
	}
	
	

}
