package it.nrsoft.nrlib.util.excel;

public class ExcelAPIWrapperFactory {
	
	public static final String APINAME_POI = "POI";
	public static final String APINAME_POIX = "POIX";

	public static ExcelAPIWrapper create(String apiname)
	{
		if(apiname.equals(APINAME_POI) || apiname.equals(APINAME_POIX))
			return new ExcelAPIWrapperPOI(apiname);
		return null;
	}

}
