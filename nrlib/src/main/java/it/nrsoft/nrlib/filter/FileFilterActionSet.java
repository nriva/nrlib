package it.nrsoft.nrlib.filter;


public class FileFilterActionSet extends FileFilterActionDecorator {

	
	// SET(field:f,value:v)
	
	private int field=-1;
	private String value="";	
	
	protected FileFilterActionSet(String expr) {
		super(expr,new String[] {"field","value"});
		field = Integer.valueOf(map.get("field"));
		value = map.get("value");

	}

	@Override
	public FileFilterActionResult perform(FileFilterActionContext context,
			Row row) {

		FileFilterActionResult result = super.perform(context, row);
		if(!result.equals(FileFilterActionResult.SKIP))
			row.setPart(field-1, value);
		return result;
	}

}
