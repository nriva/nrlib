package it.nrsoft.nrlib.filter;


public class FileFilterActionSkipIf extends FileFilterActionDecorator {
	
	
	private int field=-1;
	private String value="";

	protected FileFilterActionSkipIf(String expr) {
		super(expr, new String[] {"field","value"});
		field = Integer.valueOf(map.get("field"));
		value = map.get("value");
	}

	public FileFilterActionResult perform(FileFilterActionContext context,
			Row row) {
		
		FileFilterActionResult result = super.perform(context, row);
		if(!result.equals(FileFilterActionResult.SKIP))		
		
			return row.getPart(field-1).equals(value)?
				FileFilterActionResult.SKIP:
				FileFilterActionResult.OK;
		return result;
	}

}
