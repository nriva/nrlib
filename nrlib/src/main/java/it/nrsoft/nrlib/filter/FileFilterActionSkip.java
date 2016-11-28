package it.nrsoft.nrlib.filter;


public class FileFilterActionSkip extends FileFilterActionDecorator {
	
	long fromLine=0;
	long toLine=Long.MAX_VALUE;
	
	
	/**
	 * SKIP(from:n,to:m)
	 * @param expr
	 */
	
	protected FileFilterActionSkip(String expr)
	{
		super(expr,new String[] {"from","to"});
		if(map.containsKey("from"))
			fromLine = Long.parseLong(map.get("from"));
		if(map.containsKey("to"))
			toLine = Long.parseLong(map.get("to"));
	}
	

	public FileFilterActionResult perform(FileFilterActionContext context,
			Row row) {

		FileFilterActionResult result = super.perform(context, row);
		if(!result.equals(FileFilterActionResult.SKIP))
			return fromLine <= context.getCurrLine() && context.getCurrLine() <= toLine? 
				FileFilterActionResult.SKIP
				: FileFilterActionResult.OK;
		return result;
	}

}
