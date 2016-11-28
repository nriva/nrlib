package it.nrsoft.nrlib.filter;

public interface FileFilterAction {
	
	FileFilterActionResult perform(FileFilterActionContext context,Row row);

}
