package it.nrsoft.nrlib.mapping;

import java.util.List;
import java.util.Map;

public abstract class MappingMacro {
	
	MappingRulesEvaluator evaluator;
	
	public abstract Object compute(List<String> params,Map<String,Object> env);

}
