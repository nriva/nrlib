package it.nrsoft.nrlib.filter;



public class FileFilterActionBlank extends FileFilterActionDecorator {
	
	private int field=-1;

	protected FileFilterActionBlank(String expr) {
		super(expr,new String[] {"field"});
		
		if(map.containsKey("field"))
			field = Integer.parseInt(map.get("field"));

	}

	@Override
	public FileFilterActionResult perform(FileFilterActionContext context,
			Row row) {
		
		FileFilterActionResult result = super.perform(context, row);
		if(!result.equals(FileFilterActionResult.SKIP))
		{
			row.setPart(field-1, "");
		}
		
		return result;
	}

}
