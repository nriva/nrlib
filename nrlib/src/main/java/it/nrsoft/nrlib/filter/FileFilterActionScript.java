package it.nrsoft.nrlib.filter;

public class FileFilterActionScript extends FileFilterActionDecorator {

	private String scriptId;

	protected FileFilterActionScript(String expr) {
		super(expr,new String[] {"script"});
		scriptId = map.get("script");

	}

	@Override
	public FileFilterActionResult perform(FileFilterActionContext context,
			Row row) {
		
		
		FileFilterActionResult result = super.perform(context, row);
		
		
		/*
		
		if(!result.equals(FileFilterActionResult.SKIP))
		{
		
		
			ScriptEngine engine = new ScriptEngineGroovy();
			
			engine.getEnviron().clear();
			engine.getEnviron().putAll(row.getAsMap());
			
			Object ok = engine.executeByName(scriptId);
			row.setFromMap(engine.getEnviron());
			
			return (Boolean)ok?
					FileFilterActionResult.OK:
					FileFilterActionResult.SKIP;
		}
		*/
		return result;
	}

}
