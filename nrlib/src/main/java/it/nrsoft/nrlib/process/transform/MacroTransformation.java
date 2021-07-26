package it.nrsoft.nrlib.process.transform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.nrsoft.nrlib.mapping.MappingMacro;
import it.nrsoft.nrlib.mapping.MappingMacroConcat;
import it.nrsoft.nrlib.mapping.MappingMacroConst;
import it.nrsoft.nrlib.mapping.MappingMacroGUID;
import it.nrsoft.nrlib.mapping.MappingMacroGroovy;
import it.nrsoft.nrlib.mapping.MappingMacroNow;
import it.nrsoft.nrlib.mapping.MappingMacroSystemProp;
import it.nrsoft.nrlib.mapping.MappingMacroUniqueId;
import it.nrsoft.nrlib.mapping.MappingMacroUsername;
import it.nrsoft.nrlib.mapping.MappingMacroVariable;
import it.nrsoft.nrlib.process.DataRow;

public class MacroTransformation extends Transformation {
	
	static Map<String,MappingMacro> macros = new HashMap<String, MappingMacro>();
	
	private MappingMacro macro = null;
	List<String> params = null;
	
	static {
		macros.put("now", new MappingMacroNow());
		macros.put("uniqueId", new MappingMacroUniqueId());
		macros.put("user", new MappingMacroUsername());
		macros.put("const", new MappingMacroConst());
		macros.put("sys", new MappingMacroSystemProp());
		macros.put("guid", new MappingMacroGUID());
		macros.put("var", new MappingMacroVariable());
	}	

	
	
	public MacroTransformation(String macroDef) {
		
		if(!macroDef.startsWith("@"))
			throw new IllegalArgumentException(macroDef + " does not start with @");
		
		int openParentIdx = macroDef.indexOf("(");
		int closeParentIdx = macroDef.indexOf(")");
		
		boolean check = true;
		if ((openParentIdx!=-1 && closeParentIdx==-1)||(openParentIdx==-1 && closeParentIdx!=-1))
			check = false;
		
		String macroName = "";
		if(openParentIdx==-1 && closeParentIdx==-1 ) {
			macroName = macroDef.substring(1);
			params = new LinkedList<>();
		}
		else {
			macroName = macroDef.substring(1, openParentIdx);
			String macroParams = macroDef.substring(openParentIdx+1, closeParentIdx);
			params = Arrays.asList(macroParams.split(","));
		}
		
		
		
		if(!macros.containsKey(macroName)) {
			throw new IllegalArgumentException(macroName + " is not valid macro name");
		}
		
		macro = macros.get(macroName);
		
	}

	@Override
	public Object apply(Map<String, Object> variables, DataRow row) {


		return macro.compute(params, variables);
	}

}
