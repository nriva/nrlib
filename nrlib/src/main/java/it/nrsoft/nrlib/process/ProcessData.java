package it.nrsoft.nrlib.process;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ProcessData {
	
	protected InitialProperties initProperies;
	
	protected Map<String, Object> variables = new HashMap<>();
	
	protected List<DataRow> data = new LinkedList<>();

	public ProcessData(InitialProperties initProperies) {
		super();
		this.initProperies = initProperies;
	}

	public InitialProperties getInitProperies() {
		return initProperies;
	}
	
	public void addDataRow(DataRow row) {
		data.add(row);
	}

	public List<DataRow> getDataRows() {
		return data;
	}
	
	public void setDataRows(List<DataRow> rows) {
		this.data.clear();
		for(DataRow row: rows) {
			this.data.add(row);
		}
	}
	

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables; 
		
	}

	public Map<String, Object> getVariables() {
		return variables;
	}



}
