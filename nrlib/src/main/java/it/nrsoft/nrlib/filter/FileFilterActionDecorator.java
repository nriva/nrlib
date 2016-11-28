package it.nrsoft.nrlib.filter;

import java.util.Map;

import it.nrsoft.nrlib.util.StringUtil;


/*
 * Tipi di azioni:
 * 	SKIP(from:m,to:m)
 *  SKIPIF(field:f,value:v)
 *  SET(field:f,value:v)
 *  COPY(from:f1,to:f2)
 *  BLANK(field:f)
 *  SCRIPT(script:action)
 */

public abstract class FileFilterActionDecorator implements FileFilterAction {
	
	protected Map<String,String> map;
	
	protected FileFilterAction decoratedAction;
	
	
	public FileFilterActionDecorator setDecoratedAction(FileFilterAction decoratedAction) {
		this.decoratedAction = decoratedAction;
		return this;
	}

	protected FileFilterActionDecorator(String expr,String[] keys) {
		map = StringUtil.dictionarySplit(expr, ",", ":",keys);
	}
	
	public static FileFilterActionDecorator parse(String expr)
	{
		FileFilterActionDecorator action = null;
		if(expr.toLowerCase().startsWith("skipif"))
		{
			action = new FileFilterActionSkipIf(expr.substring(7,expr.length()-1));
		}
		else if(expr.toLowerCase().startsWith("skip"))
		{
			action = new FileFilterActionSkip(expr.substring(5,expr.length()-1));
		}
		else if(expr.toLowerCase().startsWith("set"))
		{
			action = new FileFilterActionSet(expr.substring(4,expr.length()-1));
		}
		else if(expr.toLowerCase().startsWith("copy"))
		{
			action = new FileFilterActionCopy(expr.substring(5,expr.length()-1));
		}
		else if(expr.toLowerCase().startsWith("blank"))
		{
			action = new FileFilterActionBlank(expr.substring(6,expr.length()-1));
		}
		
		else if(expr.toLowerCase().startsWith("script"))
		{
			action = new FileFilterActionScript(expr.substring(7,expr.length()-1));
		}

		return action;
	}

	@Override
	public FileFilterActionResult perform(FileFilterActionContext context, Row row) {

		if(decoratedAction!=null)
			return decoratedAction.perform(context, row);
		return FileFilterActionResult.OK;
	}
	
	

}
