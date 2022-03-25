package it.nrsoft.nrlib.filter;


public class FileFilterActionCopy extends FileFilterActionDecorator {
	
	private int from=-1;
	private int to=-1;	


	protected FileFilterActionCopy(String expr) {
		super(expr,new String[] {"from","to"});
		if(map.containsKey("from"))
			from = Integer.parseInt(map.get("from"));
		if(map.containsKey("to"))
			to = Integer.parseInt(map.get("to"));

	}

	@Override
	public FileFilterActionResult perform(FileFilterActionContext context,
			Row row) {
		FileFilterActionResult result = super.perform(context, row);
		if(!result.equals(FileFilterActionResult.SKIP))
			row.setPart(to-1, row.getPart(from-1));
		return result;
	}

}
