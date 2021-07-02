package it.nrsoft.nrlib.process.transform;

import java.util.Map;

import it.nrsoft.nrlib.process.DataRow;



public class IdentityTransformation extends Transformation {
	
	
	private String srcField;

	public IdentityTransformation(String srcField) {
		this.srcField = srcField;
	}

	@Override
	public Object apply(Map<String, Object> variables, DataRow row) {
		return row.get(srcField);
	}

}
